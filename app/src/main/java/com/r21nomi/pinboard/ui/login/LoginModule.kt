package com.r21nomi.pinboard.ui.login

import com.r21nomi.core.model.LoginModel
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/01/29.
 */
@Module
class LoginModule {
    @Provides
    @LoginScope
    fun provideLoginViewModel(loginModel: LoginModel): LoginViewModel {
        return LoginViewModel(loginModel)
    }
}