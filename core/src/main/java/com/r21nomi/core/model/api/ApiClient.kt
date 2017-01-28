package com.r21nomi.core.model.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Ryota Niinomi on 2016/09/26.
 */
interface ApiClient {

    @GET("/oauth")
    fun getOauth(
            @Query("response_type") responseType: String,
            @Query("redirect_uri") redirectUri: String,
            @Query("client_id") clientId: String,
            @Query("scope") scope: String,
            @Query("state") state: String
    ): Observable<Void>
}