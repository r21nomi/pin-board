package com.r21nomi.pinboard.ui.common.binding_adapter

import android.databinding.BindingAdapter
import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView
import com.r21nomi.core.pin.entity.Image

/**
 * Created by r21nomi on 2017/03/24.
 */
object SimpleDraweeViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("image", "maximumItemWidth")
    fun loadImage(view: SimpleDraweeView, image: Image, maximumItemWidth: Int) {
        view.setImageURI(Uri.parse(image.url), null)

        val param = view.layoutParams
        param.height = image.height * maximumItemWidth / image.width
        view.layoutParams = param
    }
}