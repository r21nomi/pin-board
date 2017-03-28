package com.r21nomi.pinboard.ui.pin_detail

import android.databinding.BindingAdapter
import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by r21nomi on 2017/03/24.
 */
object SimpleDraweeViewBindingAdapter {
    @kotlin.jvm.JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: SimpleDraweeView, imageUrl: String) {
        view.setImageURI(Uri.parse(imageUrl), null)
    }
}