package com.r21nomi.core.login.usecase

import android.content.Intent
import rx.Observable

/**
 * Created by r21nomi on 2017/01/30.
 */
interface LoginRepository {
    fun getOAuthIntent(): Intent

    fun setAccessTokenToPref(token: String)

    fun setAccessTokenToDB(token: String)

    fun getAccessTokenFromPref(): String

    fun observeChanges(): Observable<String>
}