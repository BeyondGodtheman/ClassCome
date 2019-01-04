package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.HtmlBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.IntentUtil
import com.sunny.classcome.utils.ToastUtil
import com.sunny.classcome.utils.UserManger
import com.sunny.classcome.wxapi.WXEntryActivity
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Desc 设置
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:32
 */
class SettingActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_setting

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.setting)))

        rl_login_password.setOnClickListener(this)
        txt_about_us.setOnClickListener(this)
        txt_invitation_points.setOnClickListener(this)
        txt_help.setOnClickListener(this)
        txt_logout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_login_password -> IntentUtil.start(this, ForgetPassActivity::class.java)
            R.id.txt_about_us -> IntentUtil.start(this, AboutActivity::class.java)
            R.id.txt_invitation_points -> share()
            R.id.txt_help -> startWeb(Constant.PUB_HELP)
            R.id.txt_logout -> {
                logOut()
            }
        }
    }


    private fun startWeb(url: String) {
        showLoading()
        ApiManager.post(composites, null, url, object : ApiManager.OnResult<BaseBean<ArrayList<HtmlBean>>>() {
            override fun onSuccess(data: BaseBean<ArrayList<HtmlBean>>) {
                hideLoading()
                data.content?.data?.let {
                    if (it.size > 0) {
                        WebActivity.start(this@SettingActivity, it[0].title, it[0].content)
                    }
                    return
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    private fun share() {
        showLoading()
        ApiManager.post(composites, null, Constant.PUB_GETSHOWURL, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                data.content?.data.let {
                    WXEntryActivity.shareInvite(it ?: "")
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })

    }

    private fun logOut(){
        showLoading()
        UserManger.getLogin()?.content?.userId?.let {
            val params  = HashMap<String,String>()
            params["id"] = it
            ApiManager.post(composites,params,Constant.USER_LOGINOUT,object :ApiManager.OnResult<BaseBean<String>>(){
                override fun onSuccess(data: BaseBean<String>) {
                    hideLoading()
                    if (data.content?.statu == "1"){
                        UserManger.clear()
                        finishAfterTransition()
                    }else{
                        ToastUtil.show(data.content?.info)
                    }
                }

                override fun onFailed(code: String, message: String) {
                    hideLoading()
                }
            })
        }


    }
}