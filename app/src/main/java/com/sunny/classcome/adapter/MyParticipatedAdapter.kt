package com.sunny.classcome.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.activity.CancelPromptActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.showBlueBtn
import com.sunny.classcome.utils.showGrayBtn
import kotlinx.android.synthetic.main.item_my_class.view.*
import java.lang.StringBuilder

class MyParticipatedAdapter(list: ArrayList<ClassBean.Bean.Data>) : BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_my_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.txt_left.visibility = View.GONE
        holder.itemView.txt_mid.visibility = View.GONE
        holder.itemView.txt_right.visibility = View.GONE

        val type = when (getData(position).course.state) {

            "-1" -> {

                "驳回"
            }
            "1" -> "待审核"
            "2" -> "未中标"
            "3" -> {
                delete(holder.itemView.txt_right)
                "已取消"
            }
            "4" -> {
                cancel(holder.itemView.txt_right)
                "已中标"
            }
            "5" -> "待结算"
            "6" -> {
                complete(holder.itemView.txt_right)
                "完成"
            }
            "8" -> "申请退款"
            "9" -> "已退款"
            else -> "全部"
        }

        holder.itemView.txt_status.text = type
        getData(position).materialList?.let {
            if (it.isNotEmpty()){
                GlideUtil.loadPhone(context, holder.itemView.img_class_photo, it[0].url)
            }
        }

        holder.itemView.txt_title.text = getData(position).course.title
        holder.itemView.txt_money.text = ("¥" + getData(position).course.sumPrice)
        val timeSb = StringBuilder()

        getData(position).course.startTime?.let {
            timeSb.append(it.split(" ")[0])

        }
        timeSb.append("至")

        getData(position).course.endTime?.let {
            timeSb.append(it.split(" ")[0])

        }

        holder.itemView.txt_date.text = timeSb

    }


    //取消
    private fun cancel(textView: TextView) {
        textView.apply {
            showGrayBtn(this, "取消订单")
            setOnClickListener {
                context.startActivity(Intent(context,CancelPromptActivity::class.java))

            }
        }
    }


    //删除
    private fun delete(textView: TextView){
        textView.apply {
            showGrayBtn(this, "删除")
            setOnClickListener {
                context.startActivity(Intent(context,CancelPromptActivity::class.java))

            }
        }
    }

    private fun complete(textView: TextView){
        textView.apply {
            showBlueBtn(this, "评价")
            setOnClickListener {

            }
        }
    }

}