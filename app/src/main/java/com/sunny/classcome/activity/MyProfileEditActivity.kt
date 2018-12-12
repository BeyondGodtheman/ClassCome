package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_my_profile_edit.*

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

        txt_go_identity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_go_identity -> IntentUtil.start(this, PersonAuthActivity::class.java)
        }
    }
}