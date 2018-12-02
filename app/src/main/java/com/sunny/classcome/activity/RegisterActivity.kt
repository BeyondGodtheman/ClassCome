package com.sunny.classcome.activity

import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.RegBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.LogUtil
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/2 21:23
 */
class RegisterActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_register

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.reg_title)))
        val style = SpannableStringBuilder(txt_reg_protocol.text)
        style.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View?) {

            }
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

        }, 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)  //设置点击位置

        style.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_nav_blue))
                , 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)  //设置位置颜色
        txt_reg_protocol.text = style

        btn_reg_complete.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            btn_reg_complete.id -> {
                register()
            }
        }

    }

    //注册方法
    private fun register(){
        val params = HashMap<String,String>()
        params["authCode"] = edit_reg_code.text.toString()
        params["telephone"] = edit_reg_phone.text.toString()
        params["passWord"] = edit_reg_pass.text.toString()
        ApiManager.post(composites,params,Constant.USER_REGISTERUSER,object :ApiManager.OnResult<BaseBean<RegBean>>(){
            override fun onSuccess(data: BaseBean<RegBean>) {
                if (data.code != "0"){
                    ToastUtil.show(data.content?.info)
                }
            }

            override fun onFailed(code: String, message: String) {
            }
        })

    }
}