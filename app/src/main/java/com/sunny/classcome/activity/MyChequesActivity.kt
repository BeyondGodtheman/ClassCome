package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.PayInfoBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_cheques.*

/**
 * Desc 我的收款
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:09
 */
class MyChequesActivity : BaseActivity() {

    private var payInfoBean: PayInfoBean? = null

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
            R.id.txt_wechat -> BindPayActivity.intent(this, BindPayActivity.weChat,
                    payInfoBean?.openId ?: "", payInfoBean?.userName ?: "", payInfoBean?.payType
                    ?: "0")
            R.id.txt_alipay -> BindPayActivity.intent(this, BindPayActivity.aliPay,
                    payInfoBean?.payId ?: "", payInfoBean?.realName ?: "", payInfoBean?.payType
                    ?: "0")
        }
    }

    override fun update() {
        val params = HashMap<String, String>()
        ApiManager.post(composites, params, Constant.USER_GETPAYINFO, object : ApiManager.OnResult<BaseBean<PayInfoBean>>() {
            override fun onSuccess(data: BaseBean<PayInfoBean>) {
                payInfoBean = data.content?.data


                if (data.content?.data?.openId?.isNotEmpty() == true) {
                    txt_wechat.setRightText(getString(R.string.binding))
                } else {
                    txt_wechat.setRightText(getString(R.string.unbind))
                }

                if (data.content?.data?.payId?.isNotEmpty() == true) {
                    txt_alipay.setRightText(getString(R.string.binding))
                } else {
                    txt_alipay.setRightText(getString(R.string.unbind))
                }


                when (data.content?.data?.payType) {
                    "1" -> {
                        if (data.content?.data?.payId?.isNotEmpty() == true) {
                            txt_alipay.setDeafult(View.VISIBLE)
                            txt_wechat.setDeafult(View.GONE)
                        }
                    }
                    "2" -> {
                        if (data.content?.data?.openId?.isNotEmpty() == true) {
                            txt_alipay.setDeafult(View.GONE)
                            txt_wechat.setDeafult(View.VISIBLE)
                        }

                    }
                    else -> {
                        txt_alipay.setDeafult(View.GONE)
                        txt_wechat.setDeafult(View.GONE)
                    }
                }

            }

            override fun onFailed(code: String, message: String) {
            }

        })

    }

}