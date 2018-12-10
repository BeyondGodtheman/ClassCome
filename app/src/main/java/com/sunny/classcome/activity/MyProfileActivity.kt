package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity

/**
 * Desc  我的简介
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/10 22:44
 */
class MyProfileActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_my_profile

    override fun initView() {
        showTitle(titleManager.defaultTitle("我的简介", "编辑", View.OnClickListener {

        }))
    }

    override fun onClick(v: View?) {
    }
}