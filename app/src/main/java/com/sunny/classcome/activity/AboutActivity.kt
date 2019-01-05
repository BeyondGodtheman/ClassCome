package com.sunny.classcome.activity

import android.content.Intent
import android.os.SystemClock
import android.view.View
import com.sunny.classcome.BuildConfig
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    private val mHints = arrayOfNulls<Long>(5)

    override fun setLayout(): Int = R.layout.activity_about

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.about_us)))
        txt_about_version.text = BuildConfig.VERSION_NAME
        rl_version.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.rl_version -> {
                onDisplaySettingButton()
            }
        }
    }



    fun onDisplaySettingButton() {
        System.arraycopy(mHints, 1, mHints, 0, mHints.size - 1)//把从第二位至最后一位之间的数字复制到第一位至倒数第一位
        mHints[mHints.size - 1] = SystemClock.uptimeMillis()//从开机到现在的时间毫秒数
        if (SystemClock.uptimeMillis() - (mHints[0]?:0) <= 1000) {//连续点击之间间隔小于一秒，有效
            startActivity(Intent(this@AboutActivity,DebugActivity::class.java))
        }
    }

}