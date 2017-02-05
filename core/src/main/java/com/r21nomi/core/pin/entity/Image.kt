package com.r21nomi.core.pin.entity

import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Image(
        @Json(name = "url") val url: String,
        @Json(name = "width") val width: Int,
        @Json(name = "height") val height: Int
)