package com.r21nomi.pinboard.ui.main

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.ui.Navigator

/**
 * Created by r21nomi on 2017/03/24.
 */
class PinBinderViewModel(private val activity: Activity, val maximumItemWidth: Int) {

    var pin: ObservableField<Pin> = ObservableField()

    fun setItem(pin: Pin) {
        this.pin.set(pin)
    }

    fun onThumbClick(view: View) {
        Navigator.startPinDetail(activity, view, pin.get())
    }
}