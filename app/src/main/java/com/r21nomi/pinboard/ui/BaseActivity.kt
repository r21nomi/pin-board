package com.r21nomi.pinboard.ui

import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.Application
import com.r21nomi.pinboard.di.ApplicationComponent

abstract class BaseActivity: AppCompatActivity() {

    fun getApplicationComponent(): ApplicationComponent {
        return (application as Application).applicationComponent!!
    }
}
