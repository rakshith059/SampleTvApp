package quintype.com.tvappsample.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v17.leanback.app.BackgroundManager
import android.util.DisplayMetrics
import quintype.com.tvappsample.R

/**
 * Created TvAppSample by rakshith on 6/30/18.
 */

class SimpleBackgroundManager(mActivity: Activity) {
    var activity: Activity = mActivity
    var DEFAULT_BACKGROUND = R.drawable.ic_quintype
    var mBackgroundManager: BackgroundManager
    var mDrawable: Drawable

    init {
        mDrawable = activity.getDrawable(DEFAULT_BACKGROUND)
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity.window)
        activity.windowManager.defaultDisplay.getMetrics(DisplayMetrics())
    }

    fun updateBackground(mDrawable: Drawable) {
        mBackgroundManager.drawable = mDrawable
    }

    fun removeBackground() {
        mBackgroundManager.drawable = mDrawable
    }
}