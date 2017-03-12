package com.r21nomi.pinboard.ui.login

import com.r21nomi.pinboard.domain.login.GetOAuthIntent
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/01/29.
 */
@Module
class LoginModule {
    @Provides
    @LoginScope
    fun provideLoginViewModel(getOAuthIntent: GetOAuthIntent): LoginViewModel {
        return LoginViewModel(getOAuthIntent)
    }
}