package com.r21nomi.core.pin.entity

import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Page(
        @Json(name = "cursor") val cursor: String,
        @Json(name = "next") val next: String
)