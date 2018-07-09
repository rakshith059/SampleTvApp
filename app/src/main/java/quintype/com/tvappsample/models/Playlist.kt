package quintype.com.tvappsample.models

import java.util.ArrayList

/**
 * Created TvAppSample by rakshith on 7/4/18.
 */

class Playlist {

    private val playlist: MutableList<CollectionModel>
    private var currentPosition: Int = 0

    init {
        playlist = ArrayList<CollectionModel>()
        currentPosition = 0
    }

    /**
     * Clears the videos from the playlist.
     */
    fun clear() {
        playlist.clear()
    }

    /**
     * Adds a video to the end of the playlist.
     *
     * @param video to be added to the playlist.
     */
    fun add(video: CollectionModel) {
        playlist.add(video)
    }

    /**
     * Sets current position in the playlist.
     *
     * @param currentPosition
     */
    fun setCurrentPosition(currentPosition: Int) {
        this.currentPosition = currentPosition
    }

    /**
     * Returns the size of the playlist.
     *
     * @return The size of the playlist.
     */
    fun size(): Int {
        return playlist.size
    }

    /**
     * Moves to the next video in the playlist. If already at the end of the playlist, null will
     * be returned and the position will not change.
     *
     * @return The next video in the playlist.
     */
    operator fun next(): CollectionModel? {
        if (currentPosition + 1 < size()) {
            currentPosition++
            return playlist[currentPosition]
        }
        return null
    }

    /**
     * Moves to the previous video in the playlist. If the playlist is already at the beginning,
     * null will be returned and the position will not change.
     *
     * @return The previous video in the playlist.
     */
    fun previous(): CollectionModel? {
        if (currentPosition - 1 >= 0) {
            currentPosition--
            return playlist[currentPosition]
        }
        return null
    }
}