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
import com.sunny.classcome.utils.UserManger
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
        } else if(type == 2) {
            txt_name.text = getString(R.string.cancel_prompt2)
        }else if( type == 3){
            txt_name.text = ("订单取消后，已购买的订单依旧生效\n确定取消订单?")
        }else{
            txt_name.text = ("订单支持随时退款!")
        }

        txt_prompt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_prompt -> {
                if (type == 4){
                    cancelOrther()
                }else{
                    cancelPublish()
                }

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
        //1是我发布的代课 3 我发布的培训或场地
        val url = if (type == 1 || type == 3) Constant.ORDER_CACELORDER else Constant.ORDER_CACELORDEROFTEACHER
        val params = HashMap<String, String>()
        params["courseId"] = courseId
        ApiManager.post(composites, params, url, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu == "1") {
                    EventBus.getDefault().post(Posted())
                    finishAfterTransition()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            }
        })
    }

    private fun cancelOrther(){
        val params = HashMap<String, String>()
        params["courseId"] = courseId
        params["useUserId"] = UserManger.getLogin()?.content?.userId?:""
        ApiManager.post(composites, params, Constant.ORDER_CANCLE_NEWORDER, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu == "1") {
                    EventBus.getDefault().post(Posted())
                    finishAfterTransition()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            }
        })
    }
}