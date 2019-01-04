package com.sunny.classcome.fragment

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.activity.ForgetPassActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.LoginBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.DigestUtils
import com.sunny.classcome.utils.ToastUtil
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.fragment_pass_login.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/3 23:20
 */
class PassLoginFragment: BaseFragment() {

    var onChangeLogin:(()->Unit)? = null

    override fun setLayout(): Int = R.layout.fragment_pass_login

    override fun initView() {
        txt_pass_login.setOnClickListener(this)
        txt_forget_pass.setOnClickListener(this)
        edit_login_phone.addTextChangedListener(object :TextWatcher{
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

    override fun onClick(v: View) {
        when(v.id){
            txt_forget_pass.id -> {
                startActivity(Intent(context,ForgetPassActivity::class.java))
            }
            txt_pass_login.id -> {
                onChangeLogin?.invoke()
            }
            btn_login.id -> {
                login()
            }
        }
    }


    fun login(){
        if (edit_login_phone.text.isEmpty()) {
            ToastUtil.show("请输入手机号")
            return
        }

        if (edit_login_pass.text.isEmpty()) {
            ToastUtil.show("请输入密码")
            return
        }

        getBaseActivity().showLoading()
        val params = HashMap<String, String>()
        params["telephone"] = edit_login_phone.text.toString()
        params["passWord"] = DigestUtils.md5(edit_login_pass.text.toString())
        ApiManager.post(getBaseActivity().composites, params, Constant.USER_LOGINUSER, object : ApiManager.OnResult<LoginBean>() {
            override fun onSuccess(data: LoginBean) {
                getBaseActivity().hideLoading()
                if (data.content.statu != "0"){
                    UserManger.setLogin(data)
                    getBaseActivity().finishAfterTransition()
                }else{
                    ToastUtil.show(data.content.info)
                }
            }

            override fun onFailed(code: String, message: String) {
                getBaseActivity().hideLoading()
            }
        })

    }
}