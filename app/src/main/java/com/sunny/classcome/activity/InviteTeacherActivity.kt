package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.InviteBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant

class InviteTeacherActivity : BaseActivity() {
    var courseId = ""
    var pageIndex = "1"

    val list = arrayListOf<InviteBean.Bean.Data>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {

        showTitle(titleManager.defaultTitle("邀请"))

        courseId = intent.getStringExtra("courseId")

        inviteTeacher()
    }

    override fun onClick(v: View?) {
    }

    companion object {
        fun start(context: Context, courseId: String) {
            context.startActivity(Intent(context, InviteTeacherActivity::class.java)
                    .putExtra("courseId", courseId))
        }
    }

    private fun inviteTeacher() {
        val params = hashMapOf<String,String>()
        params["pageIndex"] = pageIndex
        params["courseId"] = courseId
                ApiManager.post(composites,params,Constant.ORDER_MATCHAPPLICANTLIST,object : ApiManager.OnResult<InviteBean>(){
                    override fun onSuccess(data: InviteBean) {
                        if (pageIndex == "1"){
                            list.clear()
                        }
                        list.addAll(data.content?.dataList?: arrayListOf())

                    }

                    override fun onFailed(code: String, message: String) {
                    }

                })

    }
}