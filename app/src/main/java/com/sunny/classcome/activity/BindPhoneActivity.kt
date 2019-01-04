package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_bind_phone.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/4 00:06
 */
class BindPhoneActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_bind_phone

    override fun initView() {
        showTitle(titleManager.defaultTitle(""))
        btn_login.setOnClickListener(this)
        txt_bind_code.setClick(true)
        txt_bind_code.setCallListener(View.OnClickListener {
            sendCode()
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            btn_login.id -> {
                bindPhone()
            }
        }
    }

    private fun sendCode(){
        if (edit_bind_phone.text.isEmpty()) {
            ToastUtil.show("请输入手机号")
            return
        }
        val params = HashMap<String, String>()
        params["telephone"] = edit_bind_phone.text.toString()
        ApiManager.post(composites, params, Constant.USER_SENDCODEMSGPUB, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                txt_bind_code.action()
                hideLoading()
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })
    }


    private fun bindPhone() {
        if (edit_bind_phone.text.isEmpty()) {
            ToastUtil.show("请输入手机号")
            return
        }

        if (edit_bind_code.text.isEmpty()) {
            ToastUtil.show("请输验证码")
            return
        }

        showLoading()
        val params = HashMap<String, String>()
        params["telephone"] = edit_bind_phone.text.toString()
        params["authCode"] = edit_bind_code.text.toString()
        ApiManager.post(composites, params, Constant.USER_VCHARBINDPHONE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1"){
                    finishAfterTransition()
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })
    }
}