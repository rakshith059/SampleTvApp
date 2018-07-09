package quintype.com.tvappsample.presenters

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter
import quintype.com.tvappsample.models.CollectionModel

/**
 * Created TvAppSample by rakshith on 7/3/18.
 */

class DetailDescriptionPresenter : AbstractDetailsDescriptionPresenter() {
    override fun onBindDescription(viewHolder: ViewHolder?, item: Any?) {
        var collectionModel = item as CollectionModel

        viewHolder?.title?.text = collectionModel.mCollectionTitle
        viewHolder?.body?.text = collectionModel.mCollectionDescription
    }
}