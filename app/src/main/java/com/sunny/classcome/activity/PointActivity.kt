package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PointAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.PointBean
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_point.*

/**
 * Desc 我的积分
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:58
 */
class PointActivity : BaseActivity() {

    private val list: ArrayList<PointBean> by lazy {
        arrayListOf(
                PointBean("第一次登陆", "+100", "2018.12.03"),
                PointBean("身份验证", "+200", "2018.12.01"),
                PointBean("第一次竞标成功", "+200"),
                PointBean("第一次发布成功", "+200")
        )
    }

    override fun setLayout(): Int = R.layout.activity_point

    override fun initView() {
        goneTitle()

        txt_point.text = "500"

        ll_back.setOnClickListener(this)
        ll_prompt.setOnClickListener(this)

        val noviceAdapter = PointAdapter(list)
        recl_point.layoutManager = LinearLayoutManager(this)
        recl_point.adapter = noviceAdapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_back -> finish()
            R.id.ll_prompt -> IntentUtil.start(this, PointDescActivity::class.java)
        }
    }
}