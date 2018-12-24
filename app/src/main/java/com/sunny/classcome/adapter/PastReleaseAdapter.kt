package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.StringUtil
import kotlinx.android.synthetic.main.item_past_release.view.*

//过往发布预览适配器

class PastReleaseAdapter(list:ArrayList<ClassBean.Bean.Data>): BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_past_release,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        getData(position).materialList?.let {
            if (it.isNotEmpty()){
                GlideUtil.loadPhoto(context, holder.itemView.img_class_photo, it[0].url?:"")
            }
        }

        holder.itemView.text_class_name.text = getData(position).course.title
        holder.itemView.txt_money.text = ("¥"+StringUtil.formatMoney((getData(position).course.sumPrice?:"0").toDouble()))
    }
}