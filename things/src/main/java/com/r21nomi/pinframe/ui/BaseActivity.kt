package com.r21nomi.pinframe.ui

import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinframe.di.ApplicationComponent
import com.r21nomi.pinframe.App

abstract class BaseActivity: AppCompatActivity() {

    fun getApplicationComponent(): ApplicationComponent {
        return (application as App).applicationComponent!!
    }
}
