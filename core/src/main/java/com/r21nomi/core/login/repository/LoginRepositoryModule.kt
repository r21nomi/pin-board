package com.r21nomi.core.login.repository

import com.r21nomi.core.login.usecase.LoginRepository
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/01/30.
 */
@Module
class LoginRepositoryModule {
    @Provides
    fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository {
        return loginRepositoryImpl
    }

    interface Provider {
        fun loginRepository(): LoginRepository
    }
}