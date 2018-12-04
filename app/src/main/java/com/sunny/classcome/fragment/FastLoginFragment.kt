package com.sunny.classcome.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_fast_login.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/3 23:20
 */
class FastLoginFragment: BaseFragment() {

    var onChangeLogin:(()->Unit)? = null

    override fun setLayout(): Int = R.layout.fragment_fast_login

    override fun initView() {
        txt_fast_login.setOnClickListener(this)
        edit_login_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                MyApplication.getApp().setData(Constant.LOGIN_PHONE,edit_login_phone.text.toString())
            }
        })
        btn_login.setOnClickListener(this)
    }

    fun initPhone(){
        edit_login_phone.setText(MyApplication.getApp().getData<String>(Constant.LOGIN_PHONE,false))
        edit_login_phone.setSelection(edit_login_phone.text.length)
    }


    private fun fastLogin(){
        if (edit_login_phone.text.isEmpty()) {
            ToastUtil.show("请输入手机号")
            return
        }

        if (edit_login_code.text.isEmpty()) {
            ToastUtil.show("请输验证码")
            return
        }

        getBaseActivity().showLoading()
        val params = HashMap<String, String>()
        params["telephone"] = edit_login_phone.text.toString()
        params["authCode"] = edit_login_code.text.toString()
        ApiManager.post(getBaseActivity().composites, params, Constant.USER_LOGINUSERBYTELEPHONECODE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                getBaseActivity().hideLoading()
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
                getBaseActivity().hideLoading()
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            txt_fast_login.id -> {
                onChangeLogin?.invoke()
            }
            btn_login.id -> {
                fastLogin()
            }
        }
    }


}