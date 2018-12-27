package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.activity.PayActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.utils.GlideUtil
import kotlinx.android.synthetic.main.item_pintuan.view.*

class PinTuanAdapter(var isAppointment:String,var classBean: ClassBean.Bean.Data,list: ArrayList<ClassDetailBean.Content.ResCourseVO.PintuanResponseVO>):BaseRecycleAdapter<ClassDetailBean.Content.ResCourseVO.PintuanResponseVO>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_pintuan,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        GlideUtil.loadHead(context,holder.itemView.img_photo,getData(position).userPic?:"")
        holder.itemView.txt_name.text = getData(position).userName
        holder.itemView.txt_count.text = ("还差${getData(position).shenyurenshu}人")


        holder.itemView.btn_complete.setOnClickListener {
            if (isAppointment == "1"){
                return@setOnClickListener
            }
            PayActivity.start(context,classBean,getData(position).pintuanInfo?.id)
        }
    }

}