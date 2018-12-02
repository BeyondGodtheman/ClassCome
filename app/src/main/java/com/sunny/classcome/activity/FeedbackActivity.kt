package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_feedback.*

/**
 * Desc 意见反馈
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 21:35
 */
class FeedbackActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_feedback

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.feedback)))
        txt_commit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_commit -> {
                ToastUtil.show("提交成功")
                edit_feedback.setText("")
            }
        }
    }
}