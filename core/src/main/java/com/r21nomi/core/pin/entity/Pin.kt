package com.r21nomi.core.pin.entity

import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Pin(
        @Json(name = "id") val id: String,
        @Json(name = "note") val note: String,
        @Json(name = "image") val images: Images,
        @Json(name = "creator") val creator: Creator
)