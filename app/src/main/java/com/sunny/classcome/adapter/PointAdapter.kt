package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.PointBean
import kotlinx.android.synthetic.main.item_point.view.*

/**
 * Desc 积分子条目
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 23:28
 */
class PointAdapter(list: ArrayList<PointBean>) : BaseRecycleAdapter<PointBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_point, parent, false)


    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        list[position].apply {
            holder.itemView.txt_desc.text = desc
            holder.itemView.txt_point.text = point
            holder.itemView.txt_date.text = date
        }
    }

}