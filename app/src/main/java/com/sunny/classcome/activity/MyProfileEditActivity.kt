package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity

/**
 * Desc  我的简介：编辑信息
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/10 22:44
 */
class MyProfileEditActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_my_profile_edit

    override fun initView() {
        showTitle(titleManager.defaultTitle("编辑信息"))
    }

    override fun onClick(v: View?) {
    }
}