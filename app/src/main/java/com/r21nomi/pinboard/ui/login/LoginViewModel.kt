package com.r21nomi.pinboard.ui.login

import android.view.View
import com.r21nomi.core.model.LoginModel

/**
 * Created by r21nomi on 2017/01/29.
 */
class LoginViewModel(private val loginModel: LoginModel) {

    fun onLoginButtonClick(view: View) {
        val oauthIntent = loginModel.getOAuthIntent()
        view.context.startActivity(oauthIntent)
    }
}