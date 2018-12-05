package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_bind_pay.*

/**
 * Desc 绑定支付方式
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/6 00:36
 */
class BindPayActivity : BaseActivity() {

    var payWay = wechat

    companion object {
        const val wechat = 1
        const val alipay = 2
        fun intent(context: Context, payWay: Int) {
            val intent = Intent(context, BindPayActivity::class.java)
            intent.putExtra("payWay", payWay)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.activity_bind_pay

    override fun initView() {
        payWay = intent.getIntExtra("payWay", wechat)

        val title = if (payWay == wechat) {
            txt_user.text = "微信号:"
            "绑定微信"
        } else {
            txt_user.text = "支付宝号:"
            "绑定支付宝"
        }

        showTitle(titleManager.defaultTitle(title,"修改", View.OnClickListener {


        }))


    }

    override fun onClick(v: View?) {
    }


}