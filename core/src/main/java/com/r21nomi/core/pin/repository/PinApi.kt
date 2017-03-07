package com.r21nomi.core.pin.repository

import com.r21nomi.core.pin.entity.PinResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by r21nomi on 2017/02/05.
 */
interface PinApi {
    @GET("/v1/me/pins/")
    fun getPins(
            @Query("access_token") accessToken: String,
            @Query("fields") fields: String,
            @Query("limit") limit: Int,
            @Query("cursor") cursor: String
    ): Observable<PinResponse>
}