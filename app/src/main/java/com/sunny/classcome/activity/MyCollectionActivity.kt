package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.ClassListAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassBean
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc 我的收藏
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:21
 */
class MyCollectionActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {

        showTitle(titleManager.defaultTitle(getString(R.string.my_collection)))

        val classBean = ClassBean(
                "初中英语班课教研员-要求有专业教学资质", "", "拉丁舞", "米斯特教育", "静安寺", "2018-07-01至2018-08-26", "¥4000"
        )

        val classList = arrayListOf(classBean, classBean, classBean)

        recl.layoutManager = LinearLayoutManager(this)
        recl.isNestedScrollingEnabled = false
        recl.adapter = ClassListAdapter(classList)
    }

    override fun onClick(v: View?) {

    }
}