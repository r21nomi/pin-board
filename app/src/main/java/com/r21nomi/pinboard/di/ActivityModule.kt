package com.r21nomi.pinboard.di

import android.app.Activity
import dagger.Module

/**
 * Created by Ryota Niinomi on 2016/09/27.
 */
@Module
class ActivityModule {

    val activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }
}