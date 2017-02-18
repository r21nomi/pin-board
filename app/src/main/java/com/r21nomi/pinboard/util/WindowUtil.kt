package com.nomi.artwatch.util

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager

/**
 * Created by Ryota Niinomi on 2017/02/18.
 */
object WindowUtil {

    fun getWidth(context: Context): Int {
        val display = getDisplay(context)
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun getHeight(context: Context): Int {
        val display = getDisplay(context)
        val size = Point()
        display.getSize(size)
        return size.y
    }

    fun getSize(context: Context): Point {
        val display = getDisplay(context)
        val size = Point()
        display.getSize(size)
        return size
    }

    fun dpToPx(context: Context, dp: Int): Int {
        return (getDensity(context) * dp.toFloat() + 0.5f).toInt()
    }

    fun getDensity(context: Context): Float {
        val metrics = getDisplayMetrics(context)
        return metrics.density
    }

    private fun getDisplay(context: Context): Display {
        return (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics
    }
}
