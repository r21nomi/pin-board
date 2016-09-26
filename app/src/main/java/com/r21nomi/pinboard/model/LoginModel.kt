package com.r21nomi.pinboard.model

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.util.ApiUtil
import com.r21nomi.pinboard.model.api.ApiClient
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/26.
 */
@Singleton
class LoginModel {

    val application: Application
    val apiClient: ApiClient

    @Inject
    constructor(application: Application, apiClient: ApiClient) {
        this.application = application
        this.apiClient = apiClient
    }

    fun getOAuthIntent(): Intent {
        val param: MutableList<Pair<String, String>> = ArrayList()
        param.add(Pair("client_id", application.getString(R.string.client_id)))
        param.add(Pair("scope", "read_public,write_public"))
        param.add(Pair("response_type", "token"))
        param.add(Pair("redirect_uri", "pdk" + application.getString(R.string.client_id) + "://"))

        val url: String = ApiUtil.getUrlWithQueryParams(application.getString(R.string.oauth_url), param)
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}