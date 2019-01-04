package com.sunny.classcome.activity

import android.content.Intent
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.UserBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.IntentUtil
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_my_profile_edit.*
import org.json.JSONArray

/**
 * Desc  我的简介：编辑信息
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/10 22:44
 */
class MyProfileEditActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_my_profile_edit

    override fun initView() {
        showTitle(titleManager.defaultTitle("编辑信息"))

        MyApplication.getApp().getData<UserBean>(Constant.USER_BEAN, true)?.let { bean ->
            if (bean.user?.sex == "1") {
                rbtn_man.isChecked = true
            } else {
                rbtn_woman.isChecked = true
            }

            txt_age.setText(bean.user?.age.toString())
            txt_specialty.setText(bean.user?.profession)
            txt_work.setText(bean.user?.workAge.toString())

            if (bean.user?.isAuth == "1"){
                txt_identity.text = "已认证"
            }else{
                txt_identity.text = "未认证"
            }

            bean.materialList?.let {
                view_up.setData(it)
            }

            view_up.setText(bean.user?.userInfo?:"")
        }

        txt_go_identity.setOnClickListener(this)
        txt_save.setOnClickListener(this)


        view_up.showLoading = {
            showLoading()
        }

        view_up.hideLoading = {
            hideLoading()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_go_identity -> IntentUtil.start(this, PersonAuthActivity::class.java)
            R.id.txt_save -> {
                commit()
            }
        }
    }

    private fun commit(){
        val params = hashMapOf<String, Any>()
        params["sex"] = if (rbtn_man.isChecked) "1" else "2"
        params["age"] = txt_age.text.toString()
        params["profession"] = txt_specialty.text.toString()
        params["workAge"] = txt_work.text.toString()
        params["userInfo"] = view_up.getText()

        val jsonArray =  JSONArray()
        view_up.list.forEach {
            jsonArray.put(it.url)
        }

        params["materialUrls"] = jsonArray

        ApiManager.post(composites, params, Constant.USER_EDITMYINFO, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu == "1") {
                    finishAfterTransition()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        view_up.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}