package com.sunny.classcome.activity

import android.content.Intent
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.LoginBean
import com.sunny.classcome.fragment.FastLoginFragment
import com.sunny.classcome.fragment.PassLoginFragment
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import com.sunny.classcome.wxapi.WXEntryActivity
import kotlinx.android.synthetic.main.layout_content.*
import kotlinx.android.synthetic.main.layout_default_title.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/3 22:32
 */
class LoginActivity : BaseActivity() {


    private val titleView: View by lazy {
        titleManager.defaultTitle(getString(R.string.login), getString(R.string.reg), View.OnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        })
    }


    private val passLoginFragment: PassLoginFragment by lazy {
        PassLoginFragment().apply {
            onChangeLogin = {
                titleView.txt_base_title.text = getString(R.string.fast_login)
                fastLoginFragment.initPhone()
                supportFragmentManager.beginTransaction().show(fastLoginFragment).hide(passLoginFragment).commit()
            }
        }
    }

    private val fastLoginFragment: FastLoginFragment by lazy {
        FastLoginFragment().apply {
            onChangeLogin = {
                titleView.txt_base_title.text = getString(R.string.login)
                passLoginFragment.initPhone()
                supportFragmentManager.beginTransaction().show(passLoginFragment).hide(fastLoginFragment).commit()
            }
        }
    }


    override fun setLayout(): Int = R.layout.layout_content

    override fun initView() {
        showTitle(titleView)
        supportFragmentManager.beginTransaction().add(R.id.flContent, fastLoginFragment).hide(fastLoginFragment).add(R.id.flContent, passLoginFragment).commit()
        view_weixin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            view_weixin.id -> {
                WXEntryActivity.loginWeixin()
            }
        }
    }

    override fun close() {
        MyApplication.getApp().removeData(Constant.LOGIN_PHONE)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getStringExtra("type") == "wx"){
            UserManger.getLogin()?.let {
                if (it.content.isBindPhone == "1"){
                    startActivity(Intent(MyApplication.getApp(),BindPhoneActivity::class.java))
                }else{
                    startActivity(Intent(MyApplication.getApp(),HomeActivity::class.java))
                }
            }
            finishAfterTransition()
        }
    }
}