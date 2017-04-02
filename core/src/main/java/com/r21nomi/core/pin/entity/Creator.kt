package com.r21nomi.core.pin.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Creator(
        @Json(name = "id") val id: String,
        @Json(name = "username") val username: String,
        @Json(name = "image") val creatorImage: CreatorImage
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Creator> = object : Parcelable.Creator<Creator> {
            override fun createFromParcel(source: Parcel): Creator = Creator(source)
            override fun newArray(size: Int): Array<Creator?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readParcelable<CreatorImage>(CreatorImage::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(username)
        dest?.writeParcelable(creatorImage, 0)
    }
}