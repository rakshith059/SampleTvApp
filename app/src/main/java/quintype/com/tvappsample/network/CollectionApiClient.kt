package quintype.com.tvappsample.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created TvAppSample by rakshith on 7/9/18.
 */

class CollectionApiClient {

    companion object {
        private var retrofit: Retrofit? = null
        val mCollectionBaseUrl = "https://thequint-web.staging.quintype.io"

        fun getCollectionApiClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit
                        .Builder()
                        .baseUrl(mCollectionBaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit as Retrofit
        }
    }
}