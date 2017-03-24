package com.r21nomi.core.pin.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Creator(
        @Json(name = "id") val id: String,
        @Json(name = "url") val url: String,
        @Json(name = "first_name") val firstName: String,
        @Json(name = "last_name") val lastName: String
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Creator> = object : Parcelable.Creator<Creator> {
            override fun createFromParcel(source: Parcel): Creator = Creator(source)
            override fun newArray(size: Int): Array<Creator?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(url)
        dest?.writeString(firstName)
        dest?.writeString(lastName)
    }
}