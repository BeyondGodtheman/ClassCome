package com.sunny.classcome.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.activity.LoginActivity
import com.sunny.classcome.bean.LoginBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import com.sunny.classcome.utils.UserManger
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/4 22:29
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.getApp().wxApi.handleIntent(intent, this)
    }

    companion object {
        fun loginWeixin() {
            // 判断是否安装了微信客户端
            if (!MyApplication.getApp().wxApi.isWXAppInstalled) {
                ToastUtil.show("您还未安装微信客户端！")
                return
            }

            // 发送授权登录信息，来获取code
            val req = SendAuth.Req()
            // 应用的作用域，获取个人信息
            req.scope = "snsapi_userinfo"
            req.state = "wechat_sdk_classcome"
            MyApplication.getApp().wxApi.sendReq(req)
        }


        fun shareInvite(url: String) {
            // 判断是否安装了微信客户端
            if (!MyApplication.getApp().wxApi.isWXAppInstalled) {
                ToastUtil.show("您还未安装微信客户端！")
                return
            }
            val wxWebPageObject = WXWebpageObject()
            wxWebPageObject.webpageUrl = url

            val msg = WXMediaMessage(wxWebPageObject)
            msg.title = MyApplication.getApp().getString(R.string.share_invite)
            msg.description = (UserManger.getMine()?.userName + MyApplication.getApp().getString(R.string.share_invite_desc))
            val req = SendMessageToWX.Req()
            req.message = msg
            MyApplication.getApp().wxApi.sendReq(req)
        }

        fun shareCourse(url: String,title:String,desc:String) {
            // 判断是否安装了微信客户端
            if (!MyApplication.getApp().wxApi.isWXAppInstalled) {
                ToastUtil.show("您还未安装微信客户端！")
                return
            }
            val wxWebPageObject = WXWebpageObject()
            wxWebPageObject.webpageUrl = url

            val msg = WXMediaMessage(wxWebPageObject)
            msg.title = title
            msg.description = desc
            val req = SendMessageToWX.Req()
            req.message = msg
            MyApplication.getApp().wxApi.sendReq(req)
        }

    }


    override fun onResp(resp: BaseResp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                // 获取code
                wxLogin((resp as SendAuth.Resp).code)
            }
            else -> {
                ToastUtil.show(resp.errStr)
            }
        }
        finish()
    }

    override fun onReq(resp: BaseReq) {
        finish()
    }


    private fun wxLogin(code: String) {
        val params = HashMap<String, String>()
        params["code"] = code
        ApiManager.post(null, params, Constant.VCHARLOGIN_LOGINOFVCHAR, object : ApiManager.OnResult<LoginBean>() {
            override fun onSuccess(data: LoginBean) {
                UserManger.setLogin(data)
                startActivity(Intent(MyApplication.getApp(), LoginActivity::class.java)
                        .putExtra("type", "wx"))
            }

            override fun onFailed(code: String, message: String) {
                ToastUtil.show(message)
            }
        })

    }

//    /**
//     * 获取授权口令
//     */
//    private fun getAccessToken(code: String) {
//        val url = "https://api.weixin.qq.com/sns/oauth2/access_token"
//        val params = HashMap<String, String>()
//        params["appid"] = Constant.WX_APP_ID
//        params["secret"] = Constant.WX_APP_SECRET
//        params["code"] = code
//        params["grant_type"] = "authorization_code"
//
//        // 网络请求获取access_token
//        ApiManager.get(null, params, url, object : ApiManager.OnResult<String>() {
//            override fun onSuccess(data: String) {
//                processGetAccessTokenResult(data)
//            }
//
//            override fun onFailed(code: String, message: String) {
//                ToastUtil.show(message)
//            }
//
//        })
//    }
//
//
//    private fun getUserInfo(access_token: String, openid: String) {
//        val url = "https://api.weixin.qq.com/sns/userinfo"
//        val params = HashMap<String, String>()
//        params["access_token"] = access_token
//        params["openid"] = openid
//        // 网络请求获取access_token
//        ApiManager.get(null, params, url, object : ApiManager.OnResult<String>() {
//            override fun onSuccess(data: String) {
//
//            }
//
//            override fun onFailed(code: String, message: String) {
//                ToastUtil.show(message)
//            }
//
//        })
//    }
//
//
//    fun processGetAccessTokenResult(data: String) {
//        // 验证获取授权口令返回的信息是否成功
//        if (validateSuccess(data)) {
//            // 使用Gson解析返回的授权口令信息
//            val wxToken = ApiManager.gSon.fromJson(data, LoginBean::class.java)
//            // 获取用户信息
//            getUserInfo(wxToken.access_token, wxToken.openid)
//        } else {
//            // 授权口令获取失败，解析返回错误信息
////            ToastUtil.show()
//        }
//    }
//
//    private fun validateSuccess(data: String): Boolean {
//        val errFlag = "errmsg"
//        return (errFlag.contains(data) && "ok" != data)
//                || (!"errcode".contains(data) && !errFlag.contains(data))
//    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        MyApplication.getApp().wxApi.handleIntent(intent, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MyApplication.getApp().wxApi.handleIntent(intent, this)
    }
}