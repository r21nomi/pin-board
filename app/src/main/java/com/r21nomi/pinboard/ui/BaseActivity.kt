package com.r21nomi.pinboard.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.Application
import com.r21nomi.pinboard.di.ActivityComponent
import com.r21nomi.pinboard.di.ActivityModule
import com.r21nomi.pinboard.di.ApplicationComponent
import com.r21nomi.pinboard.di.DaggerActivityComponent

abstract class BaseActivity: AppCompatActivity() {

    var activityComponent: ActivityComponent? = null

    protected abstract fun injectDependency(component: ActivityComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent().inject(this)

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(ActivityModule(this))
                .build()

        injectDependency(activityComponent!!)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return (application as Application).applicationComponent!!
    }
}
