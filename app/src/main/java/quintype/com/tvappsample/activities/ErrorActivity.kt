package quintype.com.tvappsample.activities

import android.os.Bundle
import android.app.Activity
import quintype.com.tvappsample.R

import kotlinx.android.synthetic.main.activity_error.*
import android.support.v17.leanback.app.ErrorFragment
import quintype.com.tvappsample.fragments.ErrorScreenFragment


class ErrorActivity : Activity() {

    var mErrorFragment = ErrorScreenFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testError()
    }

    private fun testError() {
        mErrorFragment = ErrorScreenFragment()
        fragmentManager.beginTransaction().add(R.id.main_browse_fragment, mErrorFragment).commit()
    }
}
