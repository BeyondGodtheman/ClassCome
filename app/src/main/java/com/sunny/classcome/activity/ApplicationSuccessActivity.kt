package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_application_success.*

class ApplicationSuccessActivity: BaseActivity() {

    var id = ""

    override fun setLayout(): Int = R.layout.activity_application_success

    override fun initView() {

        showTitle(titleManager.defaultTitle("应聘成功"))

        id = intent.getStringExtra("id")?:"1"
        btn_home.setOnClickListener(this)
        btn_my.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_home -> {
                OrderDetailActivity.start(this,id,false)
                finish()
            }
            R.id.btn_my -> {
                startActivity(Intent(this,HomeActivity::class.java))
            }
        }
    }

    companion object {
        fun start(context: Context, id:String?) {
            context.startActivity(Intent(context, ApplicationSuccessActivity::class.java)
                    .putExtra("id",id))
        }
    }
}