package com.sunny.classcome.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.*
import com.sunny.classcome.adapter.PublishAdapter
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.PublishBean
import com.sunny.classcome.utils.IntentUtil
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
                PublishBean(resources.getString(R.string.publish_train), resources.getString(R.string.publish_train_prompt), R.mipmap.ic_publish_train),
                PublishBean(resources.getString(R.string.publish_field), resources.getString(R.string.publish_field_prompt), R.mipmap.ic_publish_field),
                PublishBean(resources.getString(R.string.publish_substitute), resources.getString(R.string.publish_substitute_prompt), R.mipmap.ic_publish_substitute),
                PublishBean(resources.getString(R.string.publish_tutor), resources.getString(R.string.publish_tutor_prompt), R.mipmap.ic_publish_tutor),
                PublishBean(resources.getString(R.string.publish_activity), resources.getString(R.string.publish_activity_prompt), R.mipmap.ic_publish_activity)
        )
    }

    private val activityList = arrayListOf(
            PublishTrainActivity::class.java,
            PublishFieldActivity::class.java
    )


    override fun setLayout(): Int = R.layout.fragment_publish

    override fun initView() {
        recl_publish.isNestedScrollingEnabled = false
        recl_publish.setHasFixedSize(true)

        recl_publish.layoutManager = LinearLayoutManager(context)

        val adapter = PublishAdapter(list)
        recl_publish.adapter = adapter
        adapter.setOnItemClickListener { _, index ->

            when (index) {
                2 -> PublishClassActivity.start(requireContext(), "2")
                3 -> PublishClassActivity.start(requireContext(), "1")
                4 -> PublishClassActivity.start(requireContext(), "3")
                else -> {
                    IntentUtil.start(requireActivity(), activityList[index])
                }
            }

        }

    }

    override fun onClick(v: View?) {
    }

}