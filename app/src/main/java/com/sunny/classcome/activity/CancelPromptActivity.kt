package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.Posted
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_cancel_prompt.*
import org.greenrobot.eventbus.EventBus

/**
 * Desc 订单取消提示
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 00:00
 */
class CancelPromptActivity : BaseActivity() {
    private var type = 1
    private var courseId = ""
    override fun setLayout(): Int = R.layout.activity_cancel_prompt

    override fun initView() {
        showTitle(titleManager.defaultTitle("取消提示"))
        type = intent.getIntExtra("type", 1)
        courseId = intent.getStringExtra("courseId")

        if (type == 1) {
            txt_name.text = getString(R.string.cancel_prompt)
        } else {
            txt_name.text = getString(R.string.cancel_prompt2)
        }

        txt_prompt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_prompt -> {
                cancelPublish()
            }
        }
    }

    companion object {
        fun start(context: Context, type: Int, courseId: String) {
            context.startActivity(Intent(context, CancelPromptActivity::class.java)
                    .putExtra("type", type)
                    .putExtra("courseId", courseId))
        }
    }

    //取消我发布的
    private fun cancelPublish() {
        val url = if (type == 1) Constant.ORDER_CACELORDER else Constant.ORDER_CACELORDEROFTEACHER
        val params = HashMap<String, String>()
        params["courseId"] = courseId
        ApiManager.post(composites, params, url, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu == "1") {
                    EventBus.getDefault().post(Posted())
                    finish()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            }
        })
    }
}