package com.r21nomi.pinboard.ui.common.data_binding

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by r21nomi on 2017/04/15.
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("image")
    fun loadImage(view: ImageView, uri: Uri) {
        Glide.with(view.context)
                .load(uri)
                .into(view)
    }
}