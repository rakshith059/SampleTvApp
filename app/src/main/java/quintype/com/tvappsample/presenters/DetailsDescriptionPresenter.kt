package quintype.com.tvappsample.presenters

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter
import quintype.com.tvappsample.models.CollectionModel

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
            viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
            item: Any) {
        val collectionModel = item as CollectionModel

        viewHolder.title.text = collectionModel.mCollectionTitle
        viewHolder.subtitle.text = collectionModel.mCollectionDescription
        viewHolder.body.text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
    }
}