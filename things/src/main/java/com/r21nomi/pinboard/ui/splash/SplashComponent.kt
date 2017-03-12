package com.r21nomi.pinboard.ui.splash

import com.r21nomi.pinboard.di.ApplicationComponent
import dagger.Component

/**
 * Created by r21nomi on 2017/03/12.
 */
@Component(
        dependencies = arrayOf(ApplicationComponent::class)
)
@SplashScope
interface SplashComponent {
    fun inject(activity: SplashActivity)
}