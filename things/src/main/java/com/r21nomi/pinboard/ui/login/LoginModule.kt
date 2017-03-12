package com.r21nomi.pinboard.ui.login

import com.r21nomi.core.login.usecase.LoginRepository
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/03/12.
 */
@Module
class LoginModule {

    @Provides
    @LoginScope
    fun provideLoginViewModel(loginRepository: LoginRepository): LoginViewModel {
        return LoginViewModel(loginRepository)
    }
}