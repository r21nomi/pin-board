package com.r21nomi.core.pin.entity

import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class Creator(
        @Json(name = "id") val id: String,
        @Json(name = "url") val url: String,
        @Json(name = "first_name") val firstName: String,
        @Json(name = "last_name") val lastName: String
)