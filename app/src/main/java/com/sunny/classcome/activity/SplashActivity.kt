package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_splash

    override fun initView() {
        btnNet.setOnClickListener(this)
    }

    override fun update() {
    }

    override fun loadData() {
    }

    override fun close() {
    }

    override fun onClick(view: View) {
    }
}