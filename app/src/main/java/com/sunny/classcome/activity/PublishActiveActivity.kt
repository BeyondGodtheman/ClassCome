package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity

/**
 * Desc 发布活动
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/9 23:35
 */
class PublishActiveActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_publish_active

    override fun initView() {
        showTitle(titleManager.defaultTitle("发布活动"))
    }

    override fun onClick(v: View?) {

    }
}