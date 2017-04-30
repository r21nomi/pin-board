package com.r21nomi.pinboard.ui.main

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableField
import android.support.v4.app.SharedElementCallback
import android.support.v7.widget.RecyclerView
import android.view.View
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.ui.Navigator
import com.r21nomi.pinboard.ui.pin_detail.PinDetailActivity

/**
 * Created by r21nomi on 2017/03/24.
 */
class PinBinderViewModel(private val activity: Activity, val maximumItemWidth: Int) {

    var pin: ObservableField<Pin> = ObservableField()
    var position: ObservableField<Int> = ObservableField()

    fun setItem(pin: Pin, position: Int) {
        this.pin.set(pin)
        this.position.set(position)
    }

    fun onThumbClick(view: View) {
        Navigator.startPinDetail(activity, view, pin.get(), position.get())
    }
}