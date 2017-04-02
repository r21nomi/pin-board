package com.r21nomi.pinboard.util

import android.app.Activity
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.view.View
import com.r21nomi.pinboard.R

/**
 * Created by r21nomi on 2017/03/06.
 */
class ViewUtil {
    companion object {
        fun showSnackBar(activity: Activity, @StringRes messageRes: Int) {
            showSnackBar(activity, activity.getString(messageRes))
        }

        fun showSnackBar(activity: Activity, message: String) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }

        fun getActionBarWithTransitionName(activity: Activity): View {
            val decor = activity.window.decorView
            val actionBarId = R.id.action_bar_container
            val actionBar = decor.findViewById(actionBarId)
            ViewCompat.setTransitionName(actionBar, "action_bar")
            return actionBar
        }
    }
}