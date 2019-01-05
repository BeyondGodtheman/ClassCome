package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_debug.*

class DebugActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_debug

    override fun initView() {
        showTitle( titleManager.defaultTitle("调试"))
        UserManger.getXg()?.let {
            edit_xg.setText(it.token)
        }
    }

    override fun onClick(v: View?) {

    }
}