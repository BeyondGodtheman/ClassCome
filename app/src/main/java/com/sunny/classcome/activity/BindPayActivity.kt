package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_bind_pay.*
import kotlin.text.Typography.nbsp

/**
 * Desc 绑定支付方式
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/6 00:36
 */
class BindPayActivity : BaseActivity() {

    private var payWay = weChat
    private var default = "0"

    companion object {
        const val weChat = 1
        const val aliPay = 2
        fun intent(context: Context, payWay: Int, account: String, name: String, default: String) {
            val intent = Intent(context, BindPayActivity::class.java)
            intent.putExtra("payWay", payWay)
            intent.putExtra("account", account)
            intent.putExtra("name", name)
            intent.putExtra("default", default)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.activity_bind_pay

    override fun initView() {
        payWay = intent.getIntExtra("payWay", weChat)

        default = intent.getStringExtra("default")

        val title = if (payWay == weChat) {
            txt_user.text = "微信号："
            txt_name.text = ("姓$nbsp$nbsp$nbsp${nbsp}名：")

            if (default == "2") {
                sw_choice.isChecked = true
            }

            "绑定微信"
        } else {
            txt_user.text = "支付宝号："
            txt_name.text = ("姓$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp${nbsp}名：")

            if (default == "1") {
                sw_choice.isChecked = true
            }

            "绑定支付宝"
        }

        showTitle(titleManager.defaultTitle(title, "修改", View.OnClickListener {
            commit()
        }))

        edit_name.setText(intent.getStringExtra("name"))

        edit_user.setText(intent.getStringExtra("account"))


        sw_choice.setOnCheckedChangeListener { _, isChecked ->
            default = if (isChecked) {
                if (payWay == weChat) {
                    "2"
                } else {
                    "1"
                }
            } else {
                "0"
            }

        }

    }

    override fun onClick(v: View?) {
    }


    private fun commit() {

        if (edit_user.text.isEmpty()){
            ToastUtil.show("请填写帐号！")
            return
        }

        if (edit_name.text.isEmpty()){
            ToastUtil.show("请填写姓名！")
            return
        }

        val params = HashMap<String, String>()
        when (payWay) {
            weChat -> {
                params["openId"] = edit_user.text.toString()
                params["userName"] = edit_name.text.toString()
            }

            aliPay -> {
                params["payId"] = edit_user.text.toString()
                params["realName"] = edit_name.text.toString()
            }
        }
        params["payType"] = default

        ApiManager.post(composites, params, Constant.USER_SETPAYINFO, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1") {
                    finishAfterTransition()
                }
            }

            override fun onFailed(code: String, message: String) {

            }

        })
    }

}