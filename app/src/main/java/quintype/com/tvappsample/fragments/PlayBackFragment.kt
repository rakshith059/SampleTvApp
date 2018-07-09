//package quintype.com.tvappsample.fragments
//
//import android.annotation.TargetApi
//import android.app.LoaderManager
//import android.content.CursorLoader
//import android.content.Intent
//import android.content.Loader
//import android.database.Cursor
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import android.support.v17.leanback.app.VideoFragment
//import android.support.v17.leanback.app.VideoFragmentGlueHost
//import android.support.v17.leanback.media.PlayerAdapter
//import android.support.v17.leanback.widget.*
//import android.support.v4.app.ActivityOptionsCompat
//import com.google.android.exoplayer2.ExoPlayerFactory
//import com.google.android.exoplayer2.SimpleExoPlayer
//import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
//import com.google.android.exoplayer2.source.ExtractorMediaSource
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
//import com.google.android.exoplayer2.trackselection.TrackSelector
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
//import com.google.android.exoplayer2.util.Util
//import quintype.com.tvappsample.R
//import quintype.com.tvappsample.models.CollectionModel
//import quintype.com.tvappsample.models.Playlist
//import quintype.com.tvappsample.presenters.CardPresenter
//import quintype.com.tvappsample.utils.Constants
//import quintype.com.tvappsample.utils.player.VideoPlayerGlue
//
///**
// * Created TvAppSample by rakshith on 7/4/18.
// */
//
//class PlayBackFragment : PlaybackVideo() {
//
//    private var mPlayerGlue: VideoPlayerGlue? = null
//    private var mPlayerAdapter: LeanbackPlayerAdapter? = null
//    private var mPlayer: SimpleExoPlayer? = null
//    private var mTrackSelector: TrackSelector? = null
//    private var mPlaylistActionListener: PlaylistActionListener? = null
//
//    private var mVideo: CollectionModel? = null
//    private var mPlaylist: Playlist? = null
//    private var mVideoLoaderCallbacks: VideoLoaderCallbacks? = null
//    private var mVideoCursorAdapter: CursorObjectAdapter? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        mVideo = activity.intent.extras.getParcelable<CollectionModel>(Constants.COLLECTION_ITEM)
//        mPlaylist = Playlist()
//
//        mVideoLoaderCallbacks = VideoLoaderCallbacks(mPlaylist as Playlist)
//
//        // Loads the playlist.
//        val args = Bundle()
////        args.putString(VideoContract.VideoEntry.COLUMN_CATEGORY, mVideo?.category)
////        loaderManager
////                .initLoader(VideoLoaderCallbacks.QUEUE_VIDEOS_LOADER, args, mVideoLoaderCallbacks)
//
//        mVideoCursorAdapter = setupRelatedVideosCursor()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (Util.SDK_INT > 23) {
//            initializePlayer()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (Util.SDK_INT <= 23 || mPlayer == null) {
//            initializePlayer()
//        }
//    }
//
//    /** Pauses the player.  */
//    @TargetApi(Build.VERSION_CODES.N)
//    override fun onPause() {
//        super.onPause()
//
//        if (mPlayerGlue != null && mPlayerGlue!!.isPlaying()) {
//            mPlayerGlue!!.pause()
//        }
//        if (Util.SDK_INT <= 23) {
//            releasePlayer()
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if (Util.SDK_INT > 23) {
//            releasePlayer()
//        }
//    }
//
//    private fun initializePlayer() {
//        val bandwidthMeter = DefaultBandwidthMeter()
//        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
//        mTrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
//
//        mPlayer = ExoPlayerFactory.newSimpleInstance(activity, mTrackSelector)
//        mPlayerAdapter = LeanbackPlayerAdapter(activity, mPlayer, UPDATE_DELAY)
//        mPlaylistActionListener = PlaylistActionListener(mPlaylist as Playlist)
//        mPlayerGlue = VideoPlayerGlue(activity, mPlayerAdapter as LeanbackPlayerAdapter, mPlaylistActionListener as PlaylistActionListener)
//        mPlayerGlue?.setHost(VideoFragmentGlueHost(this))
//        mPlayerGlue?.playWhenPrepared()
//
//        play(mVideo as CollectionModel)
//
//        val mRowsAdapter = initializeRelatedVideosRow()
//        adapter = mRowsAdapter
//    }
//
//    private fun releasePlayer() {
//        if (mPlayer != null) {
//            mPlayer!!.release()
//            mPlayer = null
//            mTrackSelector = null
//            mPlayerGlue = null
//            mPlayerAdapter = null
//            mPlaylistActionListener = null
//        }
//    }
//
//    private fun play(video: CollectionModel) {
//        mPlayerGlue!!.setTitle(video.mCollectionTitle)
//        mPlayerGlue!!.setSubtitle(video.mCollectionDescription)
//        prepareMediaForPlaying(Uri.parse(video.mCollectionVideoUrl))
//        mPlayerGlue!!.play()
//    }
//
//    private fun prepareMediaForPlaying(mediaSourceUri: Uri) {
//        val userAgent = Util.getUserAgent(activity, "VideoPlayerGlue")
//        val mediaSource = ExtractorMediaSource(
//                mediaSourceUri,
//                DefaultDataSourceFactory(activity, userAgent),
//                DefaultExtractorsFactory(), null, null)
//
//        mPlayer!!.prepare(mediaSource)
//    }
//
//    private fun initializeRelatedVideosRow(): ArrayObjectAdapter {
//        /*
//         * To add a new row to the mPlayerAdapter and not lose the controls row that is provided by the
//         * glue, we need to compose a new row with the controls row and our related videos row.
//         *
//         * We start by creating a new {@link ClassPresenterSelector}. Then add the controls row from
//         * the media player glue, then add the related videos row.
//         */
//        val presenterSelector = ClassPresenterSelector()
//        presenterSelector.addClassPresenter(
//                mPlayerGlue!!.getControlsRow().javaClass, mPlayerGlue!!.getPlaybackRowPresenter())
//        presenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
//
//        val rowsAdapter = ArrayObjectAdapter(presenterSelector)
//
//        rowsAdapter.add(mPlayerGlue!!.getControlsRow())
//
////        val header = HeaderItem(getString(R.string.related_movies))
////        val row = ListRow(header, mVideoCursorAdapter)
////        rowsAdapter.add(row)
//
//        setOnItemViewClickedListener(ItemViewClickedListener())
//
//        return rowsAdapter
//    }
//
////    private fun setupRelatedVideosCursor(): CursorObjectAdapter {
////        val videoCursorAdapter = CursorObjectAdapter(CardPresenter())
////        videoCursorAdapter.setMapper(VideoCursorMapper())
////
////        val args = Bundle()
////        args.putString(VideoContract.VideoEntry.COLUMN_CATEGORY, mVideo!!.category)
////        loaderManager.initLoader(RELATED_VIDEOS_LOADER, args, mVideoLoaderCallbacks)
////
////        return videoCursorAdapter
////    }
//
//    fun skipToNext() {
//        mPlayerGlue!!.next()
//    }
//
//    fun skipToPrevious() {
//        mPlayerGlue!!.previous()
//    }
//
//    fun rewind() {
//        mPlayerGlue!!.rewind()
//    }
//
//    fun fastForward() {
//        mPlayerGlue!!.fastForward()
//    }
//
//    /** Opens the video details page when a related video has been clicked.  */
//    private inner class ItemViewClickedListener : OnItemViewClickedListener {
//        override fun onItemClicked(
//                itemViewHolder: Presenter.ViewHolder,
//                item: Any,
//                rowViewHolder: RowPresenter.ViewHolder,
//                row: Row) {
//
//            if (item is MediaStore.Video) {
//                val video = item as MediaStore.Video
//
////                val intent = Intent(activity, VideoDetailsActivity::class.java)
////                intent.putExtra(VideoDetailsActivity.VIDEO, video)
////
////                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
////                        activity,
////                        (itemViewHolder.view as ImageCardView).mainImageView,
////                        VideoDetailsActivity.SHARED_ELEMENT_NAME)
////                        .toBundle()
////                activity.startActivity(intent, bundle)
//            }
//        }
//    }
//
//    /** Loads a playlist with videos from a cursor and also updates the related videos cursor.  */
//    protected inner class VideoLoaderCallbacks(private val playlist: Playlist) : LoaderManager.LoaderCallbacks<Cursor> {
//
////        private val mVideoCursorMapper = VideoCursorMapper()
//
//        override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {
//            // When loading related videos or videos for the playlist, query by category.
////            val category = args.getString(VideoContract.VideoEntry.COLUMN_CATEGORY)
////            return CursorLoader(
////                    activity,
////                    VideoContract.VideoEntry.CONTENT_URI, null,
////                    VideoContract.VideoEntry.COLUMN_CATEGORY + " = ?",
//                    arrayOf(category), null)
//        }
//
//        override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
//            if (cursor == null || !cursor.moveToFirst()) {
//                return
//            }
//            val id = loader.id
//            if (id == QUEUE_VIDEOS_LOADER) {
//                playlist.clear()
//                do {
//                    val video = mVideoCursorMapper.convert(cursor) as CollectionModel
//
//                    // Set the current position to the selected video.
//                    if (video.mCollectionId === mVideo.id) {
//                        playlist.setCurrentPosition(playlist.size())
//                    }
//
//                    playlist.add(video)
//
//                } while (cursor.moveToNext())
//            }
////            else if (id == RELATED_VIDEOS_LOADER) {
////                mVideoCursorAdapter!!.changeCursor(cursor)
////            }
//        }
//
//        override fun onLoaderReset(loader: Loader<Cursor>) {
//            mVideoCursorAdapter!!.changeCursor(null)
//        }
//
//        companion object {
//
//            internal val RELATED_VIDEOS_LOADER = 1
//            internal val QUEUE_VIDEOS_LOADER = 2
//        }
//    }
//
//    internal inner class PlaylistActionListener(private val mPlaylist: Playlist) : VideoPlayerGlue.OnActionClickedListener {
//
//        override fun onPrevious() {
//            play(mPlaylist.previous())
//        }
//
//        override fun onNext() {
//            play(mPlaylist.next())
//        }
//    }
//
//    companion object {
//
//        private val UPDATE_DELAY = 16
//    }
//}
