package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity

class RegSuccessActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_reg_success

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.reg_success)))

    }

    override fun onClick(v: View?) {
    }
}