package com.r21nomi.pinboard.ui

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.ui.pin_detail.PinDetailActivity
import com.r21nomi.pinboard.util.ViewUtil
import java.util.*

/**
 * Created by r21nomi on 2017/03/25.
 */
class Navigator {
    companion object {
        val KEY_PIN = "pin"
        val SHARED_ELEMENT_NAME = "shared_element_name"

        fun startPinDetail(activity: Activity, sharedElementView: View, pin: Pin) {
            val intent = Intent(activity, PinDetailActivity::class.java)
            intent.putExtra(KEY_PIN, pin)

            val statusBar = activity.findViewById(android.R.id.statusBarBackground)
            val navigationBar = activity.findViewById(android.R.id.navigationBarBackground)
            val actionBar = ViewUtil.getActionBar(activity)

            val pairs = ArrayList<Pair<View, String>>()
            if (statusBar != null) {
                pairs.add(Pair.create<View, String>(statusBar, statusBar!!.getTransitionName()))
            }
            if (navigationBar != null) {
                pairs.add(Pair.create<View, String>(navigationBar, navigationBar!!.getTransitionName()))
            }
            if (actionBar != null) {
                pairs.add(Pair.create<View, String>(actionBar, actionBar!!.getTransitionName()))
            }
            pairs.add(Pair.create<View, String>(sharedElementView, SHARED_ELEMENT_NAME))

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    *pairs.toTypedArray()
            ).toBundle()
            activity.startActivity(intent, options)
        }
    }
}