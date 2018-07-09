package quintype.com.tvappsample.utils

/**
 * Created TvAppSample by rakshith on 6/29/18.
 */


import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowManager
import android.widget.Toast

/**
 * A collection of utility methods, all static.
 */
object Utils {

    /**
     * Returns the screen/display size
     */
    fun getDisplaySize(context: Context): Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    /**
     * Shows a (long) toast
     */
    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * Shows a (long) toast.
     */
    fun showToast(context: Context, resourceId: Int) {
        Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show()
    }

    fun convertDpToPixel(ctx: Context, dp: Int): Int {
        val density = ctx.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    /**
     * Formats time in milliseconds to hh:mm:ss string format.
     */
    fun formatMillis(millis: Int): String {
        var millis = millis
        var result = ""
        val hr = millis / 3600000
        millis %= 3600000
        val min = millis / 60000
        millis %= 60000
        val sec = millis / 1000
        if (hr > 0) {
            result += hr.toString() + ":"
        }
        if (min >= 0) {
            if (min > 9) {
                result += min.toString() + ":"
            } else {
                result += "0$min:"
            }
        }
        if (sec > 9) {
            result += sec
        } else {
            result += "0$sec"
        }
        return result
    }
}/*
     * Making sure public utility methods remain static
     */