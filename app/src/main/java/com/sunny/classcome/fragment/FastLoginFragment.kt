package com.sunny.classcome.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.http.Constant
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
    }

    fun initPhone(){
        edit_login_phone.setText(MyApplication.getApp().getData<String>(Constant.LOGIN_PHONE,false))
        edit_login_phone.setSelection(edit_login_phone.text.length)
    }

    override fun onClick(v: View) {
        when(v.id){
            txt_fast_login.id -> {
                onChangeLogin?.invoke()
            }
        }
    }
}