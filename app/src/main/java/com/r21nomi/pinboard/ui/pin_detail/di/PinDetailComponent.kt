package com.r21nomi.pinboard.ui.pin_detail.di

import com.r21nomi.pinboard.di.ApplicationComponent
import com.r21nomi.pinboard.ui.login.PinDetailScope
import com.r21nomi.pinboard.ui.pin_detail.PinDetailActivity
import com.r21nomi.pinboard.ui.pin_detail.PinDetailFragment
import dagger.Component

/**
 * Created by r21nomi on 2017/03/26.
 */
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                PinDetailModule::class
        )
)
@PinDetailScope
interface PinDetailComponent : PinDetailFragment.Component {
    fun inject(pinDetailActivity: PinDetailActivity)
}