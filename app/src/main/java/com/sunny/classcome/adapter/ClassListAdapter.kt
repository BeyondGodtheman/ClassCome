package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import kotlinx.android.synthetic.main.item_class.view.*

class ClassListAdapter(list: ArrayList<ClassBean.Bean.Data>) : BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int):
            View = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.text_class_name.text = getData(position).course.title
        val time = getData(position).course.startTime.split(" ")[0] + "至" +
                getData(position).course.endTime.split(" ")[0]
        holder.itemView.text_class_time.text = time
        holder.itemView.text_class_price.text = ("¥"+getData(position).course.sumPrice)
        holder.itemView.text_class_author.text = getData(position).course.createUser
        holder.itemView.text_class_address.text = getData(position).course.classAddress
        holder.itemView.text_class_type.text = getData(position).categoryList[0].name

        GlideUtil.loadHead(context, holder.itemView.img_user_name, getData(position).user.userPic?:"")
        GlideUtil.loadPhone(context, holder.itemView.img_class_photo, getData(position).materialList[0].url)
    }
}