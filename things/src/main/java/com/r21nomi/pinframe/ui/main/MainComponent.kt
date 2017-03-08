package com.r21nomi.pinframe.ui.main

import com.r21nomi.pinframe.di.ApplicationComponent
import dagger.Component

/**
 * Created by r21nomi on 2017/02/05.
 */
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                MainModule::class
        )
)
@MainScope
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}