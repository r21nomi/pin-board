package com.r21nomi.pinboard.di

import android.content.Context
import com.r21nomi.core.di.CoreModule
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
                CoreModule::class,
                LoginModule::class
        )
)
interface ApplicationComponent : CoreModule.Provider {
        fun applicationContext(): Context
}