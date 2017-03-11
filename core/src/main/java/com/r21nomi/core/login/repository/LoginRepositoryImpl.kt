package com.r21nomi.core.login.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.r21nomi.core.R
import com.r21nomi.core.login.usecase.LoginRepository
import com.r21nomi.core.util.ApiUtil
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/26.
 */
@Singleton
class LoginRepositoryImpl @Inject constructor(val context: Context,
                                              val accessTokenPref: AccessTokenPref,
                                              val accessTokenDao: AccessTokenDao) : LoginRepository {

    override fun getOAuthIntent(): Intent {
        val param: MutableList<Pair<String, String>> = ArrayList()
        param.add(Pair("client_id", context.getString(R.string.client_id)))
        param.add(Pair("scope", "read_public,write_public"))
        param.add(Pair("response_type", "token"))
        param.add(Pair("redirect_uri", "pdk" + context.getString(R.string.client_id) + "://"))

        val url: String = ApiUtil.getUrlWithQueryParams(context.getString(R.string.oauth_url), param)
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    override fun saveAccessToken(token: String) {
        accessTokenPref.set(token)
        accessTokenDao.set(token)
    }

    override fun getAccessToken(): String {
        return accessTokenPref.get()
    }
}