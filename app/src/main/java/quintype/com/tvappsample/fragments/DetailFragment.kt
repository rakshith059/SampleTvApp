package quintype.com.tvappsample.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Movie
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v17.leanback.app.DetailsFragment
import android.support.v17.leanback.app.DetailsFragmentBackgroundController
import android.support.v17.leanback.widget.*
import quintype.com.tvappsample.models.CollectionModel
import quintype.com.tvappsample.presenters.DetailDescriptionPresenter
import java.net.URL
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.DetailsOverviewRow
import android.support.v17.leanback.widget.ClassPresenterSelector
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import quintype.com.tvappsample.MainActivity
import quintype.com.tvappsample.R
import quintype.com.tvappsample.activities.DetailActivity
import quintype.com.tvappsample.activities.PlayBackActivity
import quintype.com.tvappsample.presenters.DetailsDescriptionPresenter
import quintype.com.tvappsample.utils.SimpleBackgroundManager
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class DetailFragment() : DetailsFragment() {

    private var mSelectedMovie: CollectionModel? = null

    private lateinit var mDetailsBackground: DetailsFragmentBackgroundController
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter

    private var simpleBackgroundManager: SimpleBackgroundManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate DetailsFragment")
        super.onCreate(savedInstanceState)

        mDetailsBackground = DetailsFragmentBackgroundController(this)
        simpleBackgroundManager = SimpleBackgroundManager(mActivity = activity)

        mSelectedMovie = activity.intent.getParcelableExtra(DetailActivity.COLLECTION_ITEM) as CollectionModel
        if (mSelectedMovie != null) {
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
//            setupRelatedMovieListRow()
            adapter = mAdapter
            initializeBackground(mSelectedMovie)
            onItemViewClickedListener = ItemViewClickedListener()
        } else {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeBackground(collectionModel: CollectionModel?) {
        mDetailsBackground.enableParallax()

        Glide.with(activity)
                .asBitmap()
                .load(mSelectedMovie?.mCollectionImageUrl)
                .apply(RequestOptions().centerCrop())
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        mDetailsBackground.coverBitmap = resource

//                        var drawableBitmap = BitmapDrawable(activity.resources, resource)
//                        simpleBackgroundManager?.updateBackground(drawableBitmap)
                    }
                })
    }

    private fun setupDetailsOverviewRow() {
        Log.d(TAG, "doInBackground: " + mSelectedMovie?.toString())
        val row = DetailsOverviewRow(mSelectedMovie)
        row.imageDrawable = ContextCompat.getDrawable(context, R.drawable.ic_error)
        val width = convertDpToPixel(context, DETAIL_THUMB_WIDTH)
        val height = convertDpToPixel(context, DETAIL_THUMB_HEIGHT)

        Glide.with(activity)
                .asBitmap()
                .load(mSelectedMovie?.mCollectionImageUrl)
                .apply(RequestOptions().centerCrop())
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        var drawableBitmap = BitmapDrawable(activity.resources, resource)
                        row.imageDrawable = drawableBitmap

//                        var drawableBitmap = BitmapDrawable(activity.resources, resource)
//                        simpleBackgroundManager?.updateBackground(drawableBitmap)
                    }
                })

//        Glide.with(activity)
//                .asBitmap()
//                .load(mSelectedMovie?.mCollectionImageUrl)
//                .apply(RequestOptions().centerCrop())
//                .into(object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        mDetailsBackground.coverBitmap = resource
//                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
//                    }
//                })

        val actionAdapter = ArrayObjectAdapter()

        actionAdapter.add(
                Action(
                        ACTION_WATCH_TRAILER,
                        resources.getString(R.string.play)))

        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor =
                ContextCompat.getColor(context, R.color.fastlane_background)

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
                activity, DetailActivity.SHARED_ELEMENT_NAME)
        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_WATCH_TRAILER) {
                val intent = Intent(context, PlayBackActivity::class.java)
                intent.putExtra(DetailActivity.COLLECTION_ITEM, mSelectedMovie)
                startActivity(intent)
            } else {
                Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

//    private fun setupRelatedMovieListRow() {
//        val subcategories = arrayOf(getString(R.string.related_movies))
//        val list = MovieList.list
//
//        Collections.shuffle(list)
//        val listRowAdapter = ArrayObjectAdapter(CardPresenter())
//        for (j in 0 until NUM_COLS) {
//            listRowAdapter.add(list[j % 5])
//        }
//
//        val header = HeaderItem(0, subcategories[0])
//        mAdapter.add(ListRow(header, listRowAdapter))
//        mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
//    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
                itemViewHolder: Presenter.ViewHolder?,
                item: Any?,
                rowViewHolder: RowPresenter.ViewHolder,
                row: Row) {
            if (item is Movie) {
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(resources.getString(R.string.collection), mSelectedMovie)

                val bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                (itemViewHolder?.view as ImageCardView).mainImageView,
                                DetailActivity.SHARED_ELEMENT_NAME)
                                .toBundle()
                activity.startActivity(intent, bundle)
            }
        }
    }

    companion object {
        private val TAG = "VideoDetailsFragment"

        private val ACTION_WATCH_TRAILER = 1L

        private val DETAIL_THUMB_WIDTH = 274
        private val DETAIL_THUMB_HEIGHT = 274
    }
}


//class DetailFragment() : DetailsFragment() {
//    private var mSelectedCollection: CollectionModel = CollectionModel()
//    var mDetailBuilderTask: DetailsRowBuilderTask? = null
//    var customFullDetail: FullWidthDetailsOverviewRowPresenter? = null
//
//    var simpleBackgroundManager: SimpleBackgroundManager? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        simpleBackgroundManager = SimpleBackgroundManager(mActivity = activity)
//        mSelectedCollection = activity.intent.getParcelableExtra(DetailActivity.COLLECTION_ITEM)
//
//        Glide.with(activity)
//                .asBitmap()
//                .load(mSelectedCollection?.mCollectionImageUrl)
//                .apply(RequestOptions().centerCrop())
//                .into(object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        var drawableBitmap = BitmapDrawable(activity.resources, resource)
//                        simpleBackgroundManager?.updateBackground(drawableBitmap)
//                    }
//                })
//
//        customFullDetail = FullWidthDetailsOverviewRowPresenter(DetailDescriptionPresenter())
//        mDetailBuilderTask = activity?.let { DetailsRowBuilderTask(mSelectedCollection).execute(mSelectedCollection) } as DetailsRowBuilderTask
//    }
//
//    inner class DetailsRowBuilderTask(mSelectedCollection: CollectionModel?) : AsyncTask<CollectionModel, Int, DetailsOverviewRow>() {
//        var mSelectedCollection = mSelectedCollection
//
//        override fun doInBackground(vararg params: CollectionModel?): DetailsOverviewRow {
//            var detailsOverviewRow: DetailsOverviewRow = DetailsOverviewRow(mSelectedCollection)
//
//            var mRowBitmap = BitmapFactory.decodeStream(URL(mSelectedCollection?.mCollectionImageUrl).openConnection().getInputStream())
//            detailsOverviewRow?.setImageBitmap(activity, mRowBitmap)
//
//            return detailsOverviewRow
//        }
//
//        override fun onPostExecute(mDetailsOverviewRow: DetailsOverviewRow?) {
//            super.onPostExecute(mDetailsOverviewRow)
//            var spareArrayObjectAdapter = SparseArrayObjectAdapter()
//            spareArrayObjectAdapter.set(1, Action(1, "PLAY"))
//            spareArrayObjectAdapter.set(2, Action(2, "OVERVIEW"))
//
//            mDetailsOverviewRow?.actionsAdapter = spareArrayObjectAdapter
//
//            customFullDetail?.initialState = FullWidthDetailsOverviewRowPresenter.STATE_SMALL
//            var mCardPresenterSelector: ClassPresenterSelector = ClassPresenterSelector()
//            mCardPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, customFullDetail)
//
//            val arrayObjectAdapter = ArrayObjectAdapter(mCardPresenterSelector)
//            arrayObjectAdapter.add(mDetailsOverviewRow)
//            setAdapter(arrayObjectAdapter)
//
////            val classPresenterSelector = ClassPresenterSelector()
////            classPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, customFullDetail)
////            classPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
////
////            setAdapter(ArrayObjectAdapter(classPresenterSelector))
//        }
//    }
//
//    override fun onStop() {
//        mDetailBuilderTask?.cancel(true)
//        super.onStop()
//    }
//}


