package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.PublishBean
import kotlinx.android.synthetic.main.item_publish.view.*

/**
 * Desc 发布界面子条目
 * Author JoannChen
 * Mail q8622268@gmail.com
 * Date 2018/11/29 01:24
 */
class PublishAdapter(list: ArrayList<PublishBean>) : BaseRecycleAdapter<PublishBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_publish, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        list[position].apply {
            holder.itemView.txt_type.text = publishType
            holder.itemView.txt_info.text = publishInfo
            holder.itemView.img_icon.setImageResource(publishIcon)
        }
    }

}