package com.r21nomi.pinboard.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/09/25.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}