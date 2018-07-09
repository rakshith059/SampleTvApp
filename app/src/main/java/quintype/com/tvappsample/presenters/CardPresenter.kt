package quintype.com.tvappsample.presenters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v17.leanback.widget.BaseCardView
import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.Presenter
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import quintype.com.tvappsample.R
import quintype.com.tvappsample.models.CollectionModel


/**
 * Created TvAppSample by rakshith on 6/29/18.
 */

class CardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
//        var view: View = LayoutInflater.from(parent?.context).inflate(R.layout.header_item_row, parent, false)
//        return ViewHolder(view)

        var mImageCardView = ImageCardView(parent?.context)
        mImageCardView?.isFocusable = true
        mImageCardView?.isFocusableInTouchMode = true
        parent?.context?.resources?.getColor(R.color.fastlane_background)?.let { mImageCardView?.setBackgroundColor(it) }
        return ViewHolder(mImageCardView)
    }

    private val CARD_IMAGE_WIDTH: Int = 250
    private val CARD_IMAGE_HEIGHT: Int = 200

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        var collectionModel: CollectionModel = item as CollectionModel
//        var cvImageCard = viewHolder?.view?.findViewById<ImageCardView>(R.id.header_tem_row_cv_image_card)

//        cvImageCard?.titleText = collectionModel.mCollectionTitle
//        cvImageCard?.contentText = collectionModel.mCollectionDescription

        var icvCardView: ImageCardView = viewHolder?.view as ImageCardView
        icvCardView?.cardType = BaseCardView.CARD_TYPE_INFO_UNDER_WITH_EXTRA
        icvCardView?.titleText = collectionModel?.mCollectionTitle
        icvCardView?.contentText = collectionModel?.mCollectionDescription
        icvCardView?.setMainImageDimensions(CARD_IMAGE_WIDTH, CARD_IMAGE_HEIGHT)
        icvCardView?.setMainImageScaleType(ImageView.ScaleType.CENTER_CROP)
        icvCardView?.mainImage = viewHolder?.view?.context?.resources?.getDrawable(R.drawable.ic_quintype)

        var requestOption = RequestOptions().centerCrop()
        Glide.with(viewHolder.view.context)
                .asBitmap()
                .load(collectionModel?.mCollectionImageUrl)
                .apply(requestOption)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val bitmapDrawable = BitmapDrawable(viewHolder.view.context.resources, resource)
                        icvCardView.mainImage = bitmapDrawable
                    }
                })
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }

}