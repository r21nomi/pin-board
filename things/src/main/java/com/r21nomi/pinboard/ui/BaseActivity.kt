package com.r21nomi.pinboard.ui

import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.di.ApplicationComponent
import com.r21nomi.pinboard.App

abstract class BaseActivity: AppCompatActivity() {

    fun getApplicationComponent(): ApplicationComponent {
        return (application as App).applicationComponent!!
    }
}
