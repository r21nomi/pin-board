package com.r21nomi.pinboard.ui.pin_detail

import android.databinding.ObservableField
import com.r21nomi.core.pin.entity.Pin




/**
 * Created by r21nomi on 2017/03/24.
 */
class PinDetailViewModel(val pin: Pin, val maximumItemWidth: Int) {
    val item: ObservableField<Pin> = ObservableField()

    init {
        item.set(pin)
    }
}