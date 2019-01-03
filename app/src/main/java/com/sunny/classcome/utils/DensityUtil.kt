package com.sunny.classcome.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.Window
import com.sunny.classcome.MyApplication


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

    //获取虚拟按键的高度
    fun getNavigationBarHeight(): Int {
        var result = 0
        if (hasNavBar()) {
            val resourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId)
            }
        }
        return result
    }


    fun getTitleHeight(activity: Activity): Int {
        return activity.window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).top
    }

    /**
     * 获得状态栏的高度
     * @return
     */
    @SuppressLint("PrivateApi")
    fun getStatusHeight(): Int {

        var statusHeight = 0
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(`object`).toString())
            statusHeight = Resources.getSystem().getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }


    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    private fun hasNavBar(): Boolean {

        val resourceId = Resources.getSystem().getIdentifier("config_showNavigationBar", "bool", "android")
        return if (resourceId != 0) {
            var hasNav = Resources.getSystem().getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = getNavBarOverride()
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            hasNav
        } else { // fallback
            !ViewConfiguration.get(MyApplication.getApp()).hasPermanentMenuKey()
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    @SuppressLint("ObsoleteSdkInt", "PrivateApi")
    private fun getNavBarOverride(): String {
        var sNavBarOverride = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val c = Class.forName("android.os.SystemProperties")
                val m = c.getDeclaredMethod("get", String::class.java)
                m.isAccessible = true

                sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
            } catch (e: Throwable) {
            }
        }
        return sNavBarOverride
    }
}