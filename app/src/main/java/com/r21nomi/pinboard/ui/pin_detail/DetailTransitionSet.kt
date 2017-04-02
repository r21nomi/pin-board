package com.r21nomi.pinboard.ui.pin_detail

import android.animation.TimeInterpolator
import android.transition.ChangeBounds
import android.transition.TransitionSet
import com.github.r21nomi.androidrpinterpolator.Easing
import com.github.r21nomi.androidrpinterpolator.RPInterpolator

/**
 * Created by r21nomi on 2017/04/02.
 */
class DetailTransitionSet : TransitionSet() {

    init {
        addTransition(ChangeBounds().setInterpolator(interpolator))
    }

    override fun getInterpolator(): TimeInterpolator {
        return RPInterpolator(Easing.BACK_OUT)
    }
}