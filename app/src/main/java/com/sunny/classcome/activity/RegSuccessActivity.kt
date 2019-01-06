package com.sunny.classcome.activity

import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_reg_success.*

class RegSuccessActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_reg_success

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.reg_success)))
        btn_reg_finish.setOnClickListener(this)
        btn_reg_publish.setOnClickListener(this)
        startActivity(Intent(this,IdentityActivity::class.java))
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_reg_finish -> finish()
            R.id.btn_reg_publish -> {
              PublishClassActivity.start(this,"2")
            }
        }
    }
}