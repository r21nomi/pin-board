package com.r21nomi.pinboard

import android.app.Application
import com.r21nomi.pinboard.di.ApplicationComponent
import com.r21nomi.pinboard.di.ApplicationModule
import com.r21nomi.pinboard.di.DaggerApplicationComponent

/**
 * Created by Ryota Niinomi on 2016/09/25.
 */
open class Application: Application() {

    var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        initInjector()
    }

    fun initInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}