package com.r21nomi.pinboard.di

import com.r21nomi.pinboard.di.ApplicationModule
import com.r21nomi.pinboard.model.LoginModel
import com.r21nomi.pinboard.ui.BaseActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/25.
 */
@Singleton
@Component(
        modules = arrayOf(ApplicationModule::class)
)
interface ApplicationComponent {
    fun inject(activity: BaseActivity)

    fun loginModel(): LoginModel
}