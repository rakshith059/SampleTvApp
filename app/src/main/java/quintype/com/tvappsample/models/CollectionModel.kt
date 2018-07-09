package quintype.com.tvappsample.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created TvAppSample by rakshith on 6/29/18.
 */

class CollectionModel() : Parcelable {
    var mCollectionId: Int? = null
    var mCollectionTitle: String? = null
    var mCollectionDescription: String? = null
    var mCollectionImageUrl: String? = null
    var mCollectionVideoUrl: String? = null

    constructor(parcel: Parcel) : this() {
        mCollectionId = parcel.readValue(Int::class.java.classLoader) as Int
        mCollectionTitle = parcel.readString()
        mCollectionDescription = parcel.readString()
        mCollectionImageUrl = parcel.readString()
        mCollectionVideoUrl = parcel.readString()
    }

    constructor(collectionId: Int, collectionTitle: String, collectionDescription: String, collectionImageUrl: String, collectionVideoUrl: String) : this() {
        mCollectionId = collectionId
        mCollectionTitle = collectionTitle
        mCollectionDescription = collectionDescription
        mCollectionImageUrl = collectionImageUrl
        mCollectionVideoUrl = collectionVideoUrl
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(mCollectionId)
        parcel.writeString(mCollectionTitle)
        parcel.writeString(mCollectionDescription)
        parcel.writeString(mCollectionImageUrl)
        parcel.writeString(mCollectionVideoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CollectionModel> {
        override fun createFromParcel(parcel: Parcel): CollectionModel {
            return CollectionModel(parcel)
        }

        override fun newArray(size: Int): Array<CollectionModel?> {
            return arrayOfNulls(size)
        }
    }
}