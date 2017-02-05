package com.r21nomi.core.login.usecase

import android.content.Intent

/**
 * Created by r21nomi on 2017/01/30.
 */
interface LoginRepository {
    fun getOAuthIntent(): Intent

    fun saveAccessToken(token: String)

    fun getAccessToken(): String
}