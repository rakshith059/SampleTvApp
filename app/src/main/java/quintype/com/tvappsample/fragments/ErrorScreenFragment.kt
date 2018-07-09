package quintype.com.tvappsample.fragments

import android.os.Bundle
import android.support.v17.leanback.app.ErrorFragment
import android.view.View
import quintype.com.tvappsample.R

/**
 * A placeholder fragment containing a simple view.
 */
class ErrorScreenFragment : ErrorFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = resources.getString(R.string.error_screen)
        setErrorContent()
    }

    private fun setErrorContent() {
        imageDrawable = resources.getDrawable(R.drawable.ic_error)
        message = resources.getString(R.string.error_fragment_message)

        setDefaultBackground(true)

        buttonText = resources.getString(R.string.dismiss_error)
        buttonClickListener = object : View.OnClickListener {
            override fun onClick(arg0: View) {
                fragmentManager.beginTransaction().remove(this@ErrorScreenFragment).commit()
            }
        }
    }
}
