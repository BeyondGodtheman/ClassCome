package com.sunny.classcome.wxapi

import android.app.Activity
import android.os.Bundle
import com.sunny.classcome.MyApplication
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

class WXPayEntryActivity: Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getApp().wxApi.handleIntent(intent, this)
    }

    override fun onResp(p0: BaseResp?) {

    }

    override fun onReq(p0: BaseReq?) {

    }
}