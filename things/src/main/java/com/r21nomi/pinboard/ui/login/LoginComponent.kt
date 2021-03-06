package com.r21nomi.pinboard.ui.login

import com.r21nomi.pinboard.di.ApplicationComponent
import dagger.Component

/**
 * Created by r21nomi on 2017/03/12.
 */
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(LoginModule::class)
)
@LoginScope
interface LoginComponent {
    fun inject(activity: LoginActivity)
}