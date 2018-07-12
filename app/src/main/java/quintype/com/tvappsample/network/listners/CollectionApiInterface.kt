package quintype.com.tvappsample.network.listners

import io.reactivex.Single
import quintype.com.tvappsample.models.CollectionModel
import quintype.com.tvappsample.models.CollectionsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created TvAppSample by rakshith on 7/10/18.
 */

interface CollectionApiInterface {

    @GET("/api/v1/collections/{collectionName}")
    fun getHomeCollection(@Path("collectionName") mCollectionName: String): Call<CollectionsModel>

}