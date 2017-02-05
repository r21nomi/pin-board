package com.r21nomi.pinboard

import com.facebook.stetho.Stetho
import timber.log.Timber


/**
 * Created by Ryota Niinomi on 2017/01/29.
 */
class DebugApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
    }
}