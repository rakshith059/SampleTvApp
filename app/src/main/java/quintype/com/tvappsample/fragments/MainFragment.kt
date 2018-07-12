package quintype.com.tvappsample.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v17.leanback.app.BaseFragment
import android.support.v17.leanback.app.BrowseFragment
import android.support.v17.leanback.widget.*
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import quintype.com.tvappsample.R
import quintype.com.tvappsample.models.CollectionModel
import quintype.com.tvappsample.presenters.CardPresenter
import quintype.com.tvappsample.presenters.GridItemPresenter
import quintype.com.tvappsample.utils.SimpleBackgroundManager
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import quintype.com.tvappsample.activities.DetailActivity
import quintype.com.tvappsample.activities.ErrorActivity
import quintype.com.tvappsample.models.CollectionsModel
import quintype.com.tvappsample.network.CollectionApiClient
import quintype.com.tvappsample.network.listners.CollectionApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created TvAppSample by rakshith on 6/29/18.
 */

class MainFragment : BrowseFragment() {
    var simpleBackgroundManager: SimpleBackgroundManager? = null
    val ERROR_TEXT = "error"

//    var disposableObserver: DisposableSingleObserver<CollectionsModel>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpUiElement()
        loadRows()

//        disposableObserver = object : DisposableSingleObserver<CollectionsModel>() {
//            override fun onSuccess(mCollectionsModel: CollectionsModel) {
//                var mCollectionSummary = mCollectionsModel?.summary
//                Log.d("Rakshith", "api call success and summary is $mCollectionSummary")
//            }
//            override fun onError(e: Throwable) {
//                Log.d("Rakshith", "api call failed .. " + e.toString())
//            }
//        }
        loadRowsFromNetwork()

        simpleBackgroundManager = SimpleBackgroundManager(mActivity = activity)
        setupEventListner()
    }

    private fun loadRowsFromNetwork() {
        var collectionApiInterface: CollectionApiInterface = CollectionApiClient.getCollectionApiClient().create(CollectionApiInterface::class.java)

        collectionApiInterface.getHomeCollection("tvvideocollection")
                .enqueue(object : Callback<CollectionsModel> {
                    override fun onFailure(call: Call<CollectionsModel>?, t: Throwable?) {
                        Log.d("Rakshith", "api call failed .. " + t.toString())
                    }

                    override fun onResponse(call: Call<CollectionsModel>?, response: Response<CollectionsModel>?) {
                        var mCollectionsModel = response?.body()
                        var mCollectionSummary = mCollectionsModel?.summary
                        Log.d("Rakshith", "api call success and summary is $mCollectionSummary")
                    }
                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(disposableObserver as DisposableSingleObserver<CollectionsModel>)
    }

    private fun setupEventListner() {
        onItemViewClickedListener = ItemViewClickedListner()
        onItemViewSelectedListener = ItemSelectedListner()
    }

    private fun loadRows() {
        var mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())

        var gridItemPresenterHeader = HeaderItem(0, "TheQuint Grid Item Videos")
        var cardItemPresenterHeader = HeaderItem(1, "TheQuint Card Item Videos")

        var gridItemPresenter = GridItemPresenter()
        var cardItemPresenter = CardPresenter()

        var gridRowAdapter = ArrayObjectAdapter(gridItemPresenter)
        gridRowAdapter.add("dummy title 1")
        gridRowAdapter.add("dummy title 2")
        gridRowAdapter.add("dummy title 3")
        gridRowAdapter.add(ERROR_TEXT)
        gridRowAdapter.add("dummy title 4")
        gridRowAdapter.add("dummy title 5")

        var cardRowAdapter = ArrayObjectAdapter(cardItemPresenter)
        cardRowAdapter.add(CollectionModel(1, "dummy title 1", resources.getString(R.string.collection_description), "http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg", "https://firebasestorage.googleapis.com/v0/b/fir-sample-33949.appspot.com/o/TextInMotion-Sample-1080p.mp4?alt=media&token=7e88edb7-bfa1-4dda-bc29-2bf3e19098f4"))
        cardRowAdapter.add(CollectionModel(2, "dummy title 2", resources.getString(R.string.collection_description), "https://firebasestorage.googleapis.com/v0/b/quintype-demo.appspot.com/o/UGC%2Fstory%20title%2Fimage_upload0.png?alt=media&token=449677fd-55e1-47f2-bb17-294f1e9720dd", "https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Dance%20Search.mp4"))
        cardRowAdapter.add(CollectionModel(3, "dummy title 3", resources.getString(R.string.collection_description), "https://firebasestorage.googleapis.com/v0/b/quintype-demo.appspot.com/o/UGC%2Ftitle%2Fimage_upload0.png?alt=media&token=b148a6b3-296b-476a-8d95-820c2a91b610", "https://players.brightcove.net/5653786008001/default_default/index.html?videoId=5726510825001"))
        cardRowAdapter.add(CollectionModel(4, "dummy title 4", resources.getString(R.string.collection_description), "https://firebasestorage.googleapis.com/v0/b/quintype-demo.appspot.com/o/UGC%2Fstory%20title%2Fimage_upload0.png?alt=media&token=449677fd-55e1-47f2-bb17-294f1e9720dd", "https://www.youtube.com/watch?v=wfDSivif3FI"))
        cardRowAdapter.add(CollectionModel(5, "dummy title 5", resources.getString(R.string.collection_description), "https://firebasestorage.googleapis.com/v0/b/quintype-demo.appspot.com/o/UGC%2Fstory_title%2Fimage_upload0.png?alt=media&token=a791afa7-ffe1-4d63-8d6d-94cfe3d62ec4", "https://firebasestorage.googleapis.com/v0/b/quintype-demo.appspot.com/o/UGC%2Fnew%20story%20title%20%2Fvideo_upload_2.mp4?alt=media&token=fdee9df7-43b2-4391-9e19-943e8ccd7dce"))

        mRowsAdapter.add(ListRow(gridItemPresenterHeader, gridRowAdapter))
        mRowsAdapter.add(ListRow(cardItemPresenterHeader, cardRowAdapter))

        adapter = mRowsAdapter
    }

    private fun setUpUiElement() {
        /**
         * Can use badgeDrawable = resources.getDrawable(R.drawable.lb_in_app_search_bg)
         * instead of title
         */

        title = "TheQuint"
        headersState = BrowseFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = resources.getColor(R.color.fastlane_background)
        searchAffordanceColor = resources.getColor(R.color.search_opaque)
    }

    inner class ItemViewClickedListner : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder?, item: Any?, rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {
            if (item is String) {
                (itemViewHolder?.view as TextView).text = item as String

                if (item.equals(ERROR_TEXT)) {
                    var errorIntent = Intent(activity, ErrorActivity::class.java)
                    startActivity(errorIntent)
                }
            } else if (item is CollectionModel) {
                var collectionModel = item as CollectionModel
                Glide.with(activity)
                        .asBitmap()
                        .load(collectionModel?.mCollectionImageUrl)
                        .apply(RequestOptions().centerCrop())
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                var drawableBitmap = BitmapDrawable(activity.resources, resource)
                                simpleBackgroundManager?.updateBackground(drawableBitmap)
                            }
                        })

                var detailIntent = Intent(activity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.COLLECTION_ITEM, collectionModel)
                startActivity(detailIntent)
            }
        }
    }

    inner class ItemSelectedListner : OnItemViewSelectedListener {
        override fun onItemSelected(itemViewHolder: Presenter.ViewHolder?, item: Any?, rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {
            if (item is CollectionModel) {
                var collectionModel = item as CollectionModel
                Glide.with(activity)
                        .asBitmap()
                        .load(collectionModel?.mCollectionImageUrl)
                        .apply(RequestOptions().centerCrop())
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                var drawableBitmap = BitmapDrawable(activity.resources, resource)
                                simpleBackgroundManager?.updateBackground(drawableBitmap)
                            }
                        })
//                var detailIntent = Intent(activity, DetailActivity::class.java)
//                detailIntent.putExtra(DetailActivity.COLLECTION_ITEM, collectionModel)
//                startActivity(detailIntent)
            }
        }
    }

}
