package com.r21nomi.pinboard.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.App
import com.r21nomi.pinboard.di.ApplicationComponent

abstract class BaseActivity<C>: AppCompatActivity() {

    val component: C by lazy {
        buildComponent()
    }

    protected abstract fun buildComponent(): C

    protected abstract fun injectDependency(component: C)

    fun getApplicationComponent(): ApplicationComponent {
        return (application as App).applicationComponent!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency(component)
    }
}
