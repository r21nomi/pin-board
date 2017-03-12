package com.r21nomi.pinboard.ui.login

import android.view.View
import com.r21nomi.pinboard.domain.login.GetOAuthIntent
import rx.subjects.BehaviorSubject

/**
 * Created by r21nomi on 2017/01/29.
 */
class LoginViewModel(private val getOAuthIntent: GetOAuthIntent) {

    val loginButtonClickSubject: BehaviorSubject<Void> = BehaviorSubject.create()

    fun onLoginButtonClick(view: View) {
        val oauthIntent = getOAuthIntent.execute()
        view.context.startActivity(oauthIntent)
    }

    fun observeLoginButtonClick(): BehaviorSubject<Void> {
        return loginButtonClickSubject
    }
}