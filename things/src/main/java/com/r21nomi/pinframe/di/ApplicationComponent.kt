package com.r21nomi.pinframe.di

import com.r21nomi.core.login.repository.LoginRepositoryModule
import com.r21nomi.core.pin.repository.PinRepositoryModule
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
                PinRepositoryModule::class
        )
)
interface ApplicationComponent :
        LoginRepositoryModule.Provider,
        PinRepositoryModule.Provider {
}