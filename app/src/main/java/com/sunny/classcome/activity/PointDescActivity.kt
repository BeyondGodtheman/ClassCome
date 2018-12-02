package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PointDescAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.PointBean
import kotlinx.android.synthetic.main.activity_point_desc.*

/**
 * Desc 积分说明
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:58
 */
class PointDescActivity : BaseActivity() {

    private val noviceList: ArrayList<PointBean> by lazy {
        arrayListOf(
                PointBean("第一次登陆(注册)","+100"),
                PointBean("身份验证","+200"),
                PointBean("第一次竞标成功","+200"),
                PointBean("第一次发布成功","+200")
        )
    }

    private val dailyList: ArrayList<PointBean> by lazy {
        arrayListOf(
                PointBean("每天登陆","+10"),
                PointBean("每次竞标成功","+50"),
                PointBean("每次订单完成","+50"),
                PointBean("分享课程","+50 (单日领取上线100)"),
                PointBean("取消课程 (2小时前)","-500"),
                PointBean("取消课程 (2小时内)","-1000")
        )
    }

    override fun setLayout(): Int = R.layout.activity_point_desc

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.point_description)))

        val noviceAdapter = PointDescAdapter(noviceList)
        recl_novice.layoutManager = LinearLayoutManager(this)
        recl_novice.adapter = noviceAdapter

        val adapter = PointDescAdapter(dailyList)
        recl_daily.layoutManager = LinearLayoutManager(this)
        recl_daily.adapter = adapter
    }

    override fun onClick(v: View?) {
    }
}