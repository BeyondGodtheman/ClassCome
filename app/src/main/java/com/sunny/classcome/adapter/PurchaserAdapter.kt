package com.sunny.classcome.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.base.PurchaserBean
import kotlinx.android.synthetic.main.item_purchaser.view.*

/**
 * Desc 购买者信息
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/3 23:16
 */
class PurchaserAdapter(list: ArrayList<PurchaserBean>) : BaseRecycleAdapter<PurchaserBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_purchaser, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        list[position].apply {
            holder.itemView.img_user_head.setImageResource(R.mipmap.ic_default_head)
            holder.itemView.txt_user_name.text = userName
            holder.itemView.txt_money.text = ("支付金额：$payMoney")
            holder.itemView.txt_phone.text = ("支付金额：$contact")
            holder.itemView.txt_verify_code.text = ("支付金额：$verificationCode")


            // 核销按钮样式
            val str: String
            val bgColor: Int
            val textColor: Int
            if (isFinish) {
                str = "已核销"
                textColor = R.color.color_gray_font
                bgColor = R.drawable.draw_bg_fillet_gray_border
            } else {
                str = "核销"
                textColor = R.color.color_nav_blue
                bgColor = R.drawable.draw_bg_fillet_blue_border

            }
            holder.itemView.txt_finish.text = str
            holder.itemView.txt_finish.setTextColor(ContextCompat.getColor(context, textColor))
            holder.itemView.txt_finish.setBackgroundResource(bgColor)

            // 已核销显示评价按钮
            holder.itemView.txt_evaluate.visibility = if (isFinish) View.VISIBLE else View.GONE
        }
    }

}