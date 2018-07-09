package quintype.com.tvappsample.presenters

import android.graphics.Color
import android.support.v17.leanback.widget.Presenter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import quintype.com.tvappsample.R
import quintype.com.tvappsample.models.CollectionModel

/**
 * Created TvAppSample by rakshith on 6/29/18.
 */

class GridItemPresenter : Presenter() {


    private val GRID_ITEM_WIDTH: Int = 250
    private val GRID_ITEM_HEIGHT: Int = 200

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
//        var view: View = LayoutInflater.from(parent?.context).inflate(R.layout.header_item_row, parent, false)
//        return ViewHolder(view)

        var mTextView = TextView(parent?.context)
        mTextView.layoutParams = ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT)
        mTextView.isFocusable = true
        mTextView.isFocusableInTouchMode = true
        parent?.context?.resources?.getColor(R.color.fastlane_background)?.let { mTextView.setBackgroundColor(it) }
        mTextView.setTextColor(Color.WHITE)
        mTextView.gravity = Gravity.CENTER
        return ViewHolder(mTextView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
//        var mContext = viewHolder?.view?.context
//
//        var tvHeaderTitle = viewHolder?.view?.findViewById<TextView>(R.id.header_item_row_tv_title)
//        var ivHeaderIcon = viewHolder?.view?.findViewById<ImageView>(R.id.header_item_row_iv_icon)
//
//        var collectionModel = item as CollectionModel
//
//        tvHeaderTitle?.text = collectionModel.mCollectionTitle
//        Picasso.with(mContext)
//                .load(collectionModel.mCollectionImageUrl)
//                .placeholder(R.drawable.lb_card_foreground)
//                .error(R.drawable.ic_thequint)
//                .into(ivHeaderIcon)

//        ivHeaderIcon?.setImageResource(R.drawable.ic_thequint)

//        var collectionModel = item as CollectionModel
        (viewHolder?.view as TextView)?.setText(item as String)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}