package com.sunny.classcome.activity

import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_identity.*

class IdentityActivity: BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_identity
    override fun initView() {

        showTitle(titleManager.defaultTitle("您的身份是..."))
        txt_person.setOnClickListener(this)
        txt_company.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.txt_person -> {
                startActivity(Intent(this,ModifyInfoActivity::class.java))
                finishAfterTransition()
            }
            R.id.txt_company -> {
                startActivity(Intent(this,ModifyCompanyActivity::class.java))
                finishAfterTransition()
            }

        }

    }
}