package quintype.com.tvappsample.activities

import android.os.Bundle
import android.app.Activity
import quintype.com.tvappsample.R

import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : Activity() {
    companion object {
        const val COLLECTION_ITEM = "CollectionItem"
        const val SHARED_ELEMENT_NAME = "hero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

}
