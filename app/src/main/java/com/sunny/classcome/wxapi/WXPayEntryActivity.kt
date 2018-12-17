package com.sunny.classcome.wxapi

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import com.sunny.classcome.MyApplication
import com.sunny.classcome.utils.Pay
import com.sunny.classcome.utils.ToastUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
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

        if (resp.type == ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("提示")
            when (resp.errCode.toString()) {
                "0" -> {
                    ToastUtil.show("支付成功")
                    EventBus.getDefault().post(Pay())
                    this@WXPayEntryActivity.finish()
                }
                "-1" -> {
                    ToastUtil.show("支付失败")
                    this@WXPayEntryActivity.finish()

                }
                "-2" -> {
                    ToastUtil.show("用户取消支付")
                    this@WXPayEntryActivity.finish()
                }
            }
        }
    }
}