package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_publish_success.*

class PublishSuccessActivity: BaseActivity() {

    var courseType = "1"

    override fun setLayout(): Int = R.layout.activity_publish_success

    override fun initView() {
        showTitle(titleManager.defaultTitle("发布完成"))

        courseType = intent.getStringExtra("courseType")?:"1"

        btn_home.setOnClickListener(this)
        btn_my.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_home -> {
                startActivity(Intent(this,HomeActivity::class.java))
            }
            R.id.btn_my -> {
                MyClassActivity.start(this,1,courseType)
                finish()
            }
        }
    }

    companion object {
        fun start(context: Context,courseType:String?) {
            context.startActivity(Intent(context, PublishSuccessActivity::class.java)
                    .putExtra("courseType",courseType))
        }
    }
}