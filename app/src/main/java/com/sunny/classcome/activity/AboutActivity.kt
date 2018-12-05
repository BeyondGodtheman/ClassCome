package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.BuildConfig
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_about

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.about_us)))
        txt_about_version.text = BuildConfig.VERSION_NAME
    }

    override fun onClick(v: View) {
    }
}