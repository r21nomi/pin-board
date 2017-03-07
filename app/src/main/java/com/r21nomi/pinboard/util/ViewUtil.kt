package com.r21nomi.pinboard.util

import android.app.Activity
import android.support.design.widget.Snackbar

/**
 * Created by r21nomi on 2017/03/06.
 */
class ViewUtil {
    companion object {
        fun showSnackBar(activity: Activity, message: String) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }
    }
}