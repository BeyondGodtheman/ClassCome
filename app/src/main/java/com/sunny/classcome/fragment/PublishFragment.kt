package com.sunny.classcome.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PublishAdapter
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.PublishBean
import kotlinx.android.synthetic.main.fragment_publish.*

/**
 * Desc 发布
 * Author JoannChen
 * Mail q8622268@gmail.com
 * Date 2018/11/29 01:26
 */
class PublishFragment : BaseFragment() {

    private val list: ArrayList<PublishBean> by lazy {
        arrayListOf(
                PublishBean(resources.getString(R.string.publish_train), resources.getString(R.string.publish_train_prompt), R.mipmap.ic_nav_add_blue),
                PublishBean(resources.getString(R.string.publish_field), resources.getString(R.string.publish_field_prompt), R.mipmap.ic_nav_add_blue),
                PublishBean(resources.getString(R.string.publish_substitute), resources.getString(R.string.publish_substitute_prompt), R.mipmap.ic_nav_add_blue),
                PublishBean(resources.getString(R.string.publish_tutor), resources.getString(R.string.publish_tutor_prompt), R.mipmap.ic_nav_add_blue),
                PublishBean(resources.getString(R.string.publish_activity), resources.getString(R.string.publish_activity_prompt), R.mipmap.ic_nav_add_blue)
        )
    }


    override fun setLayout(): Int = R.layout.fragment_publish

    override fun initView() {
        recycler_view.isNestedScrollingEnabled = false
        recycler_view.setHasFixedSize(true)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = PublishAdapter(list)

    }

    override fun onClick(v: View?) {
    }

}