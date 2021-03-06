package com.sunny.classcome.wxapi

import android.app.Activity
import android.os.Bundle
import com.sunny.classcome.MyApplication
import com.sunny.classcome.utils.Pay
import com.sunny.classcome.utils.ToastUtil
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import org.greenrobot.eventbus.EventBus

class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getApp().wxApi.handleIntent(intent, this)
    }


    override fun onReq(p0: BaseReq?) {

    }


    //第三方应用发送到微信的请求处理后的响应结果，会回调该方法
    override fun onResp(resp: BaseResp) {

        when (resp.errCode.toString()) {
            "0" -> {
                ToastUtil.show("支付成功")
                EventBus.getDefault().post(Pay())
                this@WXPayEntryActivity.finishAfterTransition()
            }
            "-1" -> {
                ToastUtil.show("支付失败")
                this@WXPayEntryActivity.finishAfterTransition()

            }
            "-2" -> {
                ToastUtil.show("用户取消支付")
                this@WXPayEntryActivity.finishAfterTransition()
            }
        }
    }
}