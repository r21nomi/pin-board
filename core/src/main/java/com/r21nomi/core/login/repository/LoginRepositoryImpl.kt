package com.r21nomi.core.login.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.r21nomi.core.R
import com.r21nomi.core.login.usecase.LoginRepository
import com.r21nomi.core.model.api.ApiClient
import com.r21nomi.core.util.ApiUtil
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/26.
 */
@Singleton
class LoginRepositoryImpl @Inject constructor(val application: Application, val apiClient: ApiClient) : LoginRepository {

    companion object {
        val PREF_NAME = "login_pref"
        val PREF_KEY_ACCESS_TOKEN = "access_token"

        fun getAccessToken(context: Context) : String {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString(PREF_KEY_ACCESS_TOKEN, "")
        }

        fun setAccessToken(context: Context, accessToken: String) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            pref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).commit()
        }
    }

    override fun getOAuthIntent(): Intent {
        val param: MutableList<Pair<String, String>> = ArrayList()
        param.add(Pair("client_id", application.getString(R.string.client_id)))
        param.add(Pair("scope", "read_public,write_public"))
        param.add(Pair("response_type", "token"))
        param.add(Pair("redirect_uri", "pdk" + application.getString(R.string.client_id) + "://"))

        val url: String = ApiUtil.getUrlWithQueryParams(application.getString(R.string.oauth_url), param)
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    override fun saveAccessToken(token: String) {
        Companion.setAccessToken(application, token)
    }

    override fun getAccessToken(): String {
        return Companion.getAccessToken(application)
    }
}