package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.IntentUtil
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Desc 设置
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:32
 */
class SettingActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_setting

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.setting)))

        rl_login_password.setOnClickListener(this)
        txt_about_us.setOnClickListener(this)
        txt_invitation_points.setOnClickListener(this)
        txt_help.setOnClickListener(this)
        txt_logout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_login_password -> IntentUtil.start(this, MineActivity::class.java)
            R.id.txt_about_us -> IntentUtil.start(this, MineActivity::class.java)
            R.id.txt_invitation_points -> IntentUtil.start(this, MineActivity::class.java)
            R.id.txt_help -> IntentUtil.start(this, MineActivity::class.java)
            R.id.txt_logout -> ToastUtil.show("退出登录")
        }
    }
}