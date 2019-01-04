package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.DigestUtils
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_forget_pass.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/4 00:03
 */
class ForgetPassActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_forget_pass

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.find_pass)))
        txt_code.setClick(true)
        txt_code.setCallListener(View.OnClickListener {
            sendCode()
        })
        btn_complete.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            btn_complete.id -> {
                modifyPass()
            }
        }
    }


    private fun modifyPass() {
        if (edit_phone.text.isEmpty()) {
            ToastUtil.show("请输入手机号")
            return
        }

        if (edit_code.text.isEmpty()) {
            ToastUtil.show("请输入验证码")
            return
        }

        if (edit_new_pass.text.isEmpty()) {
            ToastUtil.show("请输入新密码")
            return
        }

        if (edit_sure_pass.text.isEmpty()) {
            ToastUtil.show("请输入确认密码")
            return
        }

        if (edit_new_pass.text.toString() != edit_sure_pass.text.toString()) {
            ToastUtil.show("确认密码和新密码不一致")
            return
        }

        showLoading()
        val params = HashMap<String, String>()
        params["telephone"] = edit_phone.text.toString()
        params["authCode"] = edit_code.text.toString()
        params["passWord"] = DigestUtils.md5(edit_sure_pass.text.toString())
        ApiManager.post(composites, params, Constant.USER_RSETPASSWORD, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1")
                    finishAfterTransition()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    private fun sendCode() {
        if (edit_phone.text.isEmpty()) {
            ToastUtil.show("请输入手机号")
            return
        }
        showLoading()
        val params = HashMap<String, String>()
        params["telephone"] = edit_phone.text.toString()
        ApiManager.post(composites, params, Constant.USER_SENDCODEOFSHORTCUT, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1") {
                    txt_code.action()
                }

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

    }
}