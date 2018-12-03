package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/4 00:06
 */
class BindPhoneActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_bind_phone
    override fun initView() {
        showTitle(titleManager.defaultTitle(""))
    }

    override fun onClick(v: View?) {
    }
}