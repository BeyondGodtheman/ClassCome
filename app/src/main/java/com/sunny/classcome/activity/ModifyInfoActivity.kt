package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_modify_info.*

/**
 * Desc 完善个人信息
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 01:39
 */
class ModifyInfoActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_modify_info

    override fun initView() {
        showTitle(titleManager.defaultTitle("完善信息"))

        rl_head.setOnClickListener(this)
        rl_name.setOnClickListener(this)
        rl_sex.setOnClickListener(this)
        rl_city.setOnClickListener(this)
        rl_phone.setOnClickListener(this)
        rl_identity.setOnClickListener(this)
        txt_commit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }
}