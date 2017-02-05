package com.r21nomi.pinboard.ui.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by r21nomi on 2017/02/05.
 */
class SquareImageView : SimpleDraweeView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val w = measuredWidth
        setMeasuredDimension(w, w)
    }
}