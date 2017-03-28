package com.r21nomi.core.pin.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Image(
        @Json(name = "url") val url: String,
        @Json(name = "width") val width: Int,
        @Json(name = "height") val height: Int
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(source: Parcel): Image = Image(source)
            override fun newArray(size: Int): Array<Image?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readInt(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(url)
        dest?.writeInt(width)
        dest?.writeInt(height)
    }
}