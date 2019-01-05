package com.sunny.classcome.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassTypeBean
import kotlinx.android.synthetic.main.item_class_type_child.view.*
import java.util.*

class SearchLabelAdapter(list: ArrayList<ClassTypeBean.SubCategory>, var selectSet: HashSet<ClassTypeBean.SubCategory>) : BaseRecycleAdapter<ClassTypeBean.SubCategory>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_class_type_child, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_type_name.text = getData(position).name

        if (selectSet.contains(getData(position))) {
            holder.itemView.txt_type_name.setBackgroundResource(R.drawable.draw_bg_fillet_blue_border)
            holder.itemView.txt_type_name.setTextColor(ContextCompat.getColor(context, R.color.color_nav_blue))
        } else {
            holder.itemView.txt_type_name.setBackgroundResource(R.drawable.draw_bg_fillet_gray_border)
            holder.itemView.txt_type_name.setTextColor(ContextCompat.getColor(context, R.color.color_gray_font))
        }

        holder.itemView.setOnClickListener {
            if (selectSet.contains(getData(position))) {
                selectSet.remove(getData(position))
            } else {
                selectSet.add(getData(position))
            }
            notifyDataSetChanged()
        }
    }
}