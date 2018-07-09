package quintype.com.tvappsample.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import quintype.com.tvappsample.fragments.VideoDetailFragment

/** Loads [PlaybackVideoFragment]. */
class PlayBackActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, VideoDetailFragment())
                .commit()
    }
}
