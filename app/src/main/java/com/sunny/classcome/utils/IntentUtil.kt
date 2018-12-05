package com.sunny.classcome.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sunny.classcome.activity.BindPayActivity


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
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        if (isFinish) {
            activity.finish()
        }
    }


}