package quintype.com.tvappsample.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v17.leanback.app.VideoSupportFragment
import android.support.v17.leanback.app.VideoSupportFragmentGlueHost
import android.support.v17.leanback.media.MediaPlayerAdapter
import android.support.v17.leanback.media.PlaybackTransportControlGlue
import android.support.v17.leanback.widget.PlaybackControlsRow
import quintype.com.tvappsample.activities.DetailActivity
import quintype.com.tvappsample.models.CollectionModel
import quintype.com.tvappsample.utils.Constants

/**
 * Created TvAppSample by rakshith on 7/9/18.
 */
class VideoDetailFragment : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>
    private var mSelectedCollection: CollectionModel = CollectionModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSelectedCollection = activity?.intent?.getParcelableExtra<CollectionModel>(DetailActivity.COLLECTION_ITEM) as CollectionModel

        val glueHost = VideoSupportFragmentGlueHost(this@VideoDetailFragment)
        val playerAdapter = MediaPlayerAdapter(context)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)

        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = glueHost
        mTransportControlGlue.title = mSelectedCollection.mCollectionTitle
        mTransportControlGlue.subtitle = mSelectedCollection.mCollectionDescription
        mTransportControlGlue.playWhenPrepared()

        playerAdapter.setDataSource(Uri.parse(mSelectedCollection.mCollectionVideoUrl))
    }

    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }
}