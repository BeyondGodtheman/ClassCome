package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import kotlinx.android.synthetic.main.item_class.view.*
import java.lang.StringBuilder

class ClassListAdapter(list: ArrayList<ClassBean.Bean.Data>) : BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int):
            View = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.text_class_name.text = getData(position).course.title

        val timeSb = StringBuilder()

        getData(position).course.startTime?.let {
            timeSb.append(it.split(" ")[0])
        }
        timeSb.append("至")

        getData(position).course.endTime?.let {
            timeSb.append(it.split(" ")[0])
        }

        holder.itemView.text_class_time.text = timeSb.toString()

        holder.itemView.text_class_price.text = ("¥"+getData(position).course.sumPrice)
        holder.itemView.text_class_author.text = getData(position).course.createUser
        holder.itemView.text_class_address.text = getData(position).course.classAddress
        getData(position).categoryList?.let {
            if (it.isNotEmpty()){
                holder.itemView.text_class_type.text = it[0].name
            }
        }


        GlideUtil.loadHead(context, holder.itemView.img_user_name, getData(position).user.userPic?:"")

        getData(position).materialList?.let {
            if (it.isNotEmpty()){
                GlideUtil.loadPhone(context, holder.itemView.img_class_photo, it[0].url)
            }
        }


    }
}