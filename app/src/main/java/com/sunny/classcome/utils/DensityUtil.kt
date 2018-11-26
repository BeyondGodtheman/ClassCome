package com.sunny.classcome.utils

import android.content.res.Resources

object DensityUtil {
    private val density = Resources.getSystem().displayMetrics.density

    fun dip2px(dp: Int): Int {
        return (dp * density).toInt()
    }

    fun dip2px(dp: Float): Int {
        return (dp * density).toInt()
    }

    fun appWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun appHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}