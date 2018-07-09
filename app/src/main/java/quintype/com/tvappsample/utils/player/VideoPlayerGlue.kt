package quintype.com.tvappsample.utils.player

import android.content.Context
import android.support.v17.leanback.media.PlaybackTransportControlGlue
import android.support.v17.leanback.widget.Action
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.PlaybackControlsRow
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import java.util.concurrent.TimeUnit

/**
 * Created TvAppSample by rakshith on 7/4/18.
 */

/**
 * Manages customizing the actions in the {@link PlaybackControlsRow}. Adds and manages the
 * following actions to the primary and secondary controls:
 *
 * <ul>
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.RepeatAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.ThumbsDownAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.ThumbsUpAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.SkipPreviousAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.SkipNextAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.FastForwardAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.RewindAction}
 * </ul>
 *
 * Note that the superclass, {@link PlaybackTransportControlGlue}, manages the playback controls
 * row.
 */
class VideoPlayerGlue(
        context: Context,
        playerAdapter: LeanbackPlayerAdapter,
        private val mActionListener: OnActionClickedListener) : PlaybackTransportControlGlue<LeanbackPlayerAdapter>(context, playerAdapter) {

    private val mRepeatAction: PlaybackControlsRow.RepeatAction
    private val mThumbsUpAction: PlaybackControlsRow.ThumbsUpAction
    private val mThumbsDownAction: PlaybackControlsRow.ThumbsDownAction
    private val mSkipPreviousAction: PlaybackControlsRow.SkipPreviousAction
    private val mSkipNextAction: PlaybackControlsRow.SkipNextAction
    private val mFastForwardAction: PlaybackControlsRow.FastForwardAction
    private val mRewindAction: PlaybackControlsRow.RewindAction

    /** Listens for when skip to next and previous actions have been dispatched.  */
    interface OnActionClickedListener {

        /** Skip to the previous item in the queue.  */
        fun onPrevious()

        /** Skip to the next item in the queue.  */
        fun onNext()
    }

    init {

        mSkipPreviousAction = PlaybackControlsRow.SkipPreviousAction(context)
        mSkipNextAction = PlaybackControlsRow.SkipNextAction(context)
        mFastForwardAction = PlaybackControlsRow.FastForwardAction(context)
        mRewindAction = PlaybackControlsRow.RewindAction(context)

        mThumbsUpAction = PlaybackControlsRow.ThumbsUpAction(context)
        mThumbsUpAction.index = PlaybackControlsRow.ThumbsUpAction.INDEX_OUTLINE
        mThumbsDownAction = PlaybackControlsRow.ThumbsDownAction(context)
        mThumbsDownAction.index = PlaybackControlsRow.ThumbsDownAction.INDEX_OUTLINE
        mRepeatAction = PlaybackControlsRow.RepeatAction(context)
    }

    override fun onCreatePrimaryActions(adapter: ArrayObjectAdapter) {
        // Order matters, super.onCreatePrimaryActions() will create the play / pause action.
        // Will display as follows:
        // play/pause, previous, rewind, fast forward, next
        //   > /||      |<        <<        >>         >|
        super.onCreatePrimaryActions(adapter)
        adapter.add(mSkipPreviousAction)
        adapter.add(mRewindAction)
        adapter.add(mFastForwardAction)
        adapter.add(mSkipNextAction)
    }

    override fun onCreateSecondaryActions(adapter: ArrayObjectAdapter?) {
        super.onCreateSecondaryActions(adapter)
        adapter!!.add(mThumbsDownAction)
        adapter.add(mThumbsUpAction)
        adapter.add(mRepeatAction)
    }

    override fun onActionClicked(action: Action) {
        if (shouldDispatchAction(action)) {
            dispatchAction(action)
            return
        }
        // Super class handles play/pause and delegates to abstract methods next()/previous().
        super.onActionClicked(action)
    }

    // Should dispatch actions that the super class does not supply callbacks for.
    private fun shouldDispatchAction(action: Action): Boolean {
        return (action === mRewindAction
                || action === mFastForwardAction
                || action === mThumbsDownAction
                || action === mThumbsUpAction
                || action === mRepeatAction)
    }

    private fun dispatchAction(action: Action) {
        // Primary actions are handled manually.
        if (action === mRewindAction) {
            rewind()
        } else if (action === mFastForwardAction) {
            fastForward()
        } else if (action is PlaybackControlsRow.MultiAction) {
            action.nextIndex()
            // Notify adapter of action changes to handle secondary actions, such as, thumbs up/down
            // and repeat.
            notifyActionChanged(
                    action,
                    controlsRow.secondaryActionsAdapter as ArrayObjectAdapter)
        }
    }

    private fun notifyActionChanged(
            action: PlaybackControlsRow.MultiAction, adapter: ArrayObjectAdapter?) {
        if (adapter != null) {
            val index = adapter.indexOf(action)
            if (index >= 0) {
                adapter.notifyArrayItemRangeChanged(index, 1)
            }
        }
    }

    override fun next() {
        mActionListener.onNext()
    }

    override fun previous() {
        mActionListener.onPrevious()
    }

    /** Skips backwards 10 seconds.  */
    fun rewind() {
        var newPosition = currentPosition - TEN_SECONDS
        newPosition = if (newPosition < 0) 0 else newPosition
        playerAdapter.seekTo(newPosition)
    }

    /** Skips forward 10 seconds.  */
    fun fastForward() {
        if (duration > -1) {
            var newPosition = currentPosition + TEN_SECONDS
            newPosition = if (newPosition > duration) duration else newPosition
            playerAdapter.seekTo(newPosition)
        }
    }

    companion object {

        private val TEN_SECONDS = TimeUnit.SECONDS.toMillis(10)
    }
}
