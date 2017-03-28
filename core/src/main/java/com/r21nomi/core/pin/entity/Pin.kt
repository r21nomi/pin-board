package com.r21nomi.core.pin.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Pin(
        @Json(name = "id") val id: String,
        @Json(name = "note") val note: String,
        @Json(name = "image") val images: Images,
        @Json(name = "creator") val creator: Creator
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Pin> = object : Parcelable.Creator<Pin> {
            override fun createFromParcel(source: Parcel): Pin = Pin(source)
            override fun newArray(size: Int): Array<Pin?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readParcelable<Images>(Images::class.java.classLoader), source.readParcelable<Creator>(Creator::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(note)
        dest?.writeParcelable(images, 0)
        dest?.writeParcelable(creator, 0)
    }
}