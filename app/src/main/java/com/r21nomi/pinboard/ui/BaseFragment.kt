package com.r21nomi.pinboard.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by r21nomi on 2017/03/27.
 */
abstract class BaseFragment<C> : Fragment() {

    protected abstract fun injectDependency(component: C)

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayout(), container, false)

        injectDependency(((activity as BaseActivity<*>).component as C))

        return view
    }
}