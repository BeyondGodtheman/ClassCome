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
import kotlinx.android.synthetic.main.activity_comment.*
import org.greenrobot.eventbus.EventBus

/**
 * Desc
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/31 23:33
 */
class CommentActivity : BaseActivity() {

    private var courseId = ""
    private var commentatorId = ""
    private var ratingCount = 3f
    override fun setLayout(): Int = R.layout.activity_comment

    override fun initView() {
        showTitle(titleManager.defaultTitle("发表评论"))
        courseId = intent.getStringExtra("courseId")
        commentatorId = intent.getStringExtra("commentatorId")

        ratingBar.setStar(ratingCount)
        ratingBar.setOnRatingChangeListener {
            ratingCount = it
        }

        btn_comment.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_comment -> {
                comment()
            }
        }
    }

    companion object {
        fun start(context: Context, courseId: String, commentatorId: String) {
            context.startActivity(Intent(context, CommentActivity::class.java)
                    .putExtra("courseId", courseId)
                    .putExtra("commentatorId", commentatorId))
        }
    }

    private fun comment() {

        if (edit_comment.text.isEmpty()) {
            ToastUtil.show("请输入评论内容!")
            return
        }
        showLoading()
        val params = HashMap<String, String>()
        params["courseId"] = courseId
        params["starLevel"] = ratingCount.toInt().toString()
        params["description"] = edit_comment.text.toString()
        params["commentatorId"] = commentatorId
        ApiManager.post(composites, params, Constant.COURSE_COMMENTUSER, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (data.content?.statu == "1") {
                    EventBus.getDefault().post(Posted())
                    finish()
                } else {
                    ToastUtil.show(data.content?.info)
                }

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })
    }
}