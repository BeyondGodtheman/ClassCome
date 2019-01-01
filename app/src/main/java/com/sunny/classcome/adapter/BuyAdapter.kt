package com.sunny.classcome.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.activity.BuyActivity
import com.sunny.classcome.activity.MyProfileActivity
import com.sunny.classcome.activity.OrderDetailActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.BuyBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.StringUtil
import kotlinx.android.synthetic.main.item_buy.view.*


class BuyAdapter(list: ArrayList<BuyBean>, var classBean: ClassBean.Bean.Data?, var onOption:(position: Int)->Unit) : BaseRecycleAdapter<BuyBean>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_buy, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        GlideUtil.loadHead(context, holder.itemView.img_user_head, getData(position).userPic ?: "")
        holder.itemView.txt_name.text = (getData(position).userName?:"")
        holder.itemView.txt_money.text = ("支付金额：${StringUtil.formatMoney((getData(position).money ?: "0").toDouble())}元")
        holder.itemView.txt_phone.text = ("联系方式：${getData(position).telephone}")
        holder.itemView.setOnClickListener { _ ->
            classBean?.let {
                OrderDetailActivity.start(context,it,getData(position))
            }

        }

        if (getData(position).state == "3"){
            holder.itemView.btn.setTextColor(ContextCompat.getColor(context,R.color.color_gray_font))
            holder.itemView.btn.text = "已核销"
            holder.itemView.btn.setBackgroundResource(R.drawable.draw_bg_fillet_gray_border)
            holder.itemView.btn.setOnClickListener {

            }
        }else{
            holder.itemView.btn.setTextColor(ContextCompat.getColor(context,R.color.color_nav_blue))
            holder.itemView.btn.text = "核销"
            holder.itemView.btn.setBackgroundResource(R.drawable.draw_bg_fillet_blue_border)
            holder.itemView.btn.setOnClickListener {
                onOption(position)
            }
        }

    }
}