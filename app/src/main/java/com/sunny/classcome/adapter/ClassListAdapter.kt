package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import kotlinx.android.synthetic.main.item_class.view.*

class ClassListAdapter(list: ArrayList<ClassBean>) : BaseRecycleAdapter<ClassBean>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int):
            View = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.text_class_name.text = getData(position).title
        holder.itemView.text_class_time.text = getData(position).time
        holder.itemView.text_class_price.text = getData(position).price
        holder.itemView.text_class_author.text = getData(position).author
        holder.itemView.text_class_address.text = getData(position).address
        holder.itemView.text_class_type.text = getData(position).type
    }
}