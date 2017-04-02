package com.r21nomi.core.pin.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/04/02.
 */
data class CreatorImage(
        @Json(name = "60x60") val image: Image
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<CreatorImage> = object : Parcelable.Creator<CreatorImage> {
            override fun createFromParcel(source: Parcel): CreatorImage = CreatorImage(source)
            override fun newArray(size: Int): Array<CreatorImage?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<Image>(Image::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(image, 0)
    }
}