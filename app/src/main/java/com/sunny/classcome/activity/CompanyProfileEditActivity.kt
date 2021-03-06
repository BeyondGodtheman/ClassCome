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
import kotlinx.android.synthetic.main.activity_company_profile_edit.*
import org.json.JSONArray

class CompanyProfileEditActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_company_profile_edit

    override fun initView() {
        showTitle(titleManager.defaultTitle("编辑信息"))

        MyApplication.getApp().getData<UserBean>(Constant.USER_BEAN, true)?.let { bean ->


            txt_company_name.setText(bean.user?.organization)
            txt_address.setText(bean.user?.address)

            view_up.setText(bean.user?.userInfo ?: "")


            if (bean.user?.relationimgId != null) {
                txt_identity.text = "已通过"
                txt_go_identity.visibility = View.GONE
            } else {
                txt_identity.text = "未通过"
                txt_go_identity.visibility = View.VISIBLE
            }

            bean.materialList?.let {
                view_up.setData(it)
            }

            view_up.setText(bean.user?.userInfo ?: "")
        }

        txt_go_identity.setOnClickListener(this)
        txt_save.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_go_identity -> IntentUtil.start(this, PersonAuthActivity::class.java)
            R.id.txt_save -> {
                commit()
            }
        }
    }

    private fun commit() {
        showLoading()
        val params = hashMapOf<String, Any>()
        params["organization"] = txt_company_name.text.toString()
        params["address"] = txt_address.text.toString()
        params["userInfo"] = view_up.getText()

        val jsonArray = JSONArray()
        view_up.list.forEach {
            jsonArray.put(it.url)
        }
        params["materialUrls"] = jsonArray

        ApiManager.post(composites, params, Constant.USER_EDITMYINFO, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (data.content?.statu == "1") {
                    finishAfterTransition()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        view_up.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}