package com.r21nomi.core.login.repository

import android.content.Context
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/03/11.
 */
class AccessTokenPref @Inject constructor(val context: Context) {
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

    fun set(token: String) {
        Companion.setAccessToken(context, token)
    }

    fun get(): String {
        return Companion.getAccessToken(context)
    }
}