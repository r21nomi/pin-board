package com.r21nomi.pinboard.ui.offline

import com.r21nomi.pinboard.di.ApplicationComponent
import dagger.Component

/**
 * Created by r21nomi on 2017/08/03.
 */
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(OfflineModule::class)
)
@OfflineScope
interface OfflineComponent {
    fun inject(activity: OfflineActivity)
}