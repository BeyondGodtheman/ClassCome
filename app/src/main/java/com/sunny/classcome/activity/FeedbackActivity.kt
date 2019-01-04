package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
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
                commit()
            }
        }
    }

    //提交意见
    fun commit() {
        if (edit_feedback.text.isEmpty()){
            ToastUtil.show("请填写意见或建议")
            return
        }

        val params = hashMapOf<String, String>()
        params["content"] = edit_feedback.text.toString()
        ApiManager.post(composites, params, Constant.PUB_SAVEUSERIDEA, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                ToastUtil.show(data.content?.info ?: "")
                if (data.content?.statu == "1") {
                    finishAfterTransition()
                }
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }
}