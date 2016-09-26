package com.r21nomi.pinboard.di

import com.r21nomi.pinboard.di.ApplicationComponent
import com.r21nomi.pinboard.ui.MainActivity
import com.r21nomi.pinboard.ui.LoginActivity
import dagger.Component

/**
 * Created by Ryota Niinomi on 2016/09/27.
 */
@ForActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class)
)
interface ActivityComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: MainActivity)
}