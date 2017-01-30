package com.r21nomi.pinboard.ui.login

import android.view.View
import com.r21nomi.core.login.usecase.GetOAuthIntent

/**
 * Created by r21nomi on 2017/01/29.
 */
class LoginViewModel(private val getOAuthIntent: GetOAuthIntent) {

    fun onLoginButtonClick(view: View) {
        val oauthIntent = getOAuthIntent.execute()
        view.context.startActivity(oauthIntent)
    }
}