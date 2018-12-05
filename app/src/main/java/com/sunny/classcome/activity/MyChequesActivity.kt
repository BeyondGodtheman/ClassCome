package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_cheques.*

/**
 * Desc 我的收款
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:09
 */
class MyChequesActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_cheques

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.my_cheques)))

        txt_detailed.setOnClickListener(this)
        txt_wechat.setOnClickListener(this)
        txt_alipay.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_detailed -> IntentUtil.start(this, MyChequesDetailActivity::class.java)
            R.id.txt_wechat -> BindPayActivity.intent(this, BindPayActivity.wechat)
            R.id.txt_alipay -> BindPayActivity.intent(this, BindPayActivity.alipay)
        }
    }


}