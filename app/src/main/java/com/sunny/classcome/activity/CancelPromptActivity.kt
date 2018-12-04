package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_cancel_prompt.*

/**
 * Desc 订单取消提示
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 00:00
 */
class CancelPromptActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_cancel_prompt

    override fun initView() {
        showTitle(titleManager.defaultTitle("取消提示"))

        txt_prompt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_prompt -> ToastUtil.show("确定取消订单")
        }
    }
}