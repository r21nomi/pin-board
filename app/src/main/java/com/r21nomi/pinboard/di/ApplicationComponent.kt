package com.r21nomi.pinboard.di

import com.r21nomi.core.model.LoginModel
import com.r21nomi.pinboard.ui.login.LoginModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/25.
 */
@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                LoginModule::class
        )
)
interface ApplicationComponent {
    fun loginModel(): LoginModel
}