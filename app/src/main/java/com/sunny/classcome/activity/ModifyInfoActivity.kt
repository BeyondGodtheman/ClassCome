package com.sunny.classcome.activity

import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_modify_info.*

/**
 * Desc 完善个人信息
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 01:39
 */
class ModifyInfoActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_modify_info

    override fun initView() {
        showTitle(titleManager.defaultTitle("完善信息"))

        rl_head.setOnClickListener(this)
        rl_name.setOnClickListener(this)
        rl_city.setOnClickListener(this)
        rl_phone.setOnClickListener(this)
        rl_identity.setOnClickListener(this)
        txt_commit.setOnClickListener(this)

        UserManger.getMine()?.let {
            GlideUtil.loadHead(this, img_head, it.userPic ?: "")
            edit_name.setText(it.userName)
            txt_phone.text = it.telephone

        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_city -> {
                startActivity(Intent(this, LocationActivity::class.java))
            }
            R.id.rl_identity -> {
                //身份验证
                startActivity(Intent(this, PersonAuthActivity::class.java))
            }
        }
    }

    override fun update() {
        super.update()
        UserManger.getAddress().let {
            if (it.isNotEmpty()) {
                txt_city.text = it.split(",")[1]
            }
        }
    }
}