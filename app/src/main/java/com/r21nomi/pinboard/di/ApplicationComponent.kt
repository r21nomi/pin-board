package com.r21nomi.pinboard.di

import com.r21nomi.core.login.repository.LoginRepositoryModule
import com.r21nomi.core.pin.repository.PinRepositoryModule
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
                LoginRepositoryModule::class,
                PinRepositoryModule::class,
                LoginModule::class
        )
)
interface ApplicationComponent :
        LoginRepositoryModule.Provider,
        PinRepositoryModule.Provider {
}