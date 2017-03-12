package com.r21nomi.pinboard.di

import com.r21nomi.core.di.CoreModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/25.
 */
@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                CoreModule::class
        )
)
interface ApplicationComponent : CoreModule.Provider {
}