package com.sunny.classcome.utils

import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.app.Activity
import android.os.Bundle


/**
 * Desc：跳转类
 * Author：JoannChen
 * Mail：yongzuo_chen@dingyuegroup.cn
 * Date：2018/11/22 0022 18:05
 */
object IntentUtil {

    /**
     * Intent跳转
     */
    fun start(activity: Activity, clazz: Class<*>, isFinish: Boolean = false) {
        val intent = Intent(activity, clazz)
        activity.startActivity(intent)
        if (isFinish) {
            activity.finish()
        }
    }

    /**
     * Intent跳转传递对象
     */
    fun start(activity: Activity, clazz: Class<*>, isFinish: Boolean = false, bundle: Bundle) {
        val intent = Intent(activity, clazz)
        intent.putExtras(bundle)
        activity.startActivity(intent)
        if (isFinish) {
            activity.finish()
        }
    }


}