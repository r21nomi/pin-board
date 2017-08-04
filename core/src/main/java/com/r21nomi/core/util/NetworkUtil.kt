package com.r21nomi.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by r21nomi on 2017/08/03.
 */
object NetworkUtil {

    fun isOnline(context: Context): Boolean {
        return getNetworkInfo(context.applicationContext)?.isConnected ?: false
    }

    fun isWifiConnected(context: Context): Boolean {
        return getNetworkInfo(context.applicationContext)?.run {
            isConnected && type == ConnectivityManager.TYPE_WIFI
        } ?: false
    }

    private fun getNetworkInfo(context: Context): NetworkInfo? {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            activeNetworkInfo
        }
    }
}