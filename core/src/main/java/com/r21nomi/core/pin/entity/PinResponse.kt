package com.r21nomi.core.pin.entity

import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2017/02/05.
 */
data class PinResponse(
        @Json(name = "data") val data: List<Pin>,
        @Json(name = "page") val page: Page
)