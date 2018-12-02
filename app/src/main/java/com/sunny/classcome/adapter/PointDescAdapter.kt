package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.PointBean
import kotlinx.android.synthetic.main.item_point_desc.view.*

/**
 * Desc 积分说明
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 23:28
 */
class PointDescAdapter(list: ArrayList<PointBean>) : BaseRecycleAdapter<PointBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_point_desc, parent, false)


    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        list[position].apply {
            holder.itemView.txt_desc.text = desc
            holder.itemView.txt_point.text = point
        }
    }

}