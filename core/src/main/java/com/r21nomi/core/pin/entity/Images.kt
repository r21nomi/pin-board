package com.r21nomi.core.pin.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Images(
        @Json(name = "original") val image: Image
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Images> = object : Parcelable.Creator<Images> {
            override fun createFromParcel(source: Parcel): Images = Images(source)
            override fun newArray(size: Int): Array<Images?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<Image>(Image::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(image, 0)
    }
}