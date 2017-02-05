package com.r21nomi.pinboard.ui.splash

import com.r21nomi.pinboard.di.ApplicationComponent
import com.r21nomi.pinboard.ui.login.SplashScope
import dagger.Component

/**
 * Created by r21nomi on 2017/02/05.
 */
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
            SplashModule::class
        )
)
@SplashScope
interface SplashComponent {
    fun inject(actvity: SplashActivity)
}