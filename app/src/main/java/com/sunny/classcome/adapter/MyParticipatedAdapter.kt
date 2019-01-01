package com.sunny.classcome.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.activity.CancelPromptActivity
import com.sunny.classcome.activity.OrderDetailActivity
import com.sunny.classcome.activity.PayActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.StringUtil
import com.sunny.classcome.utils.showBlueBtn
import com.sunny.classcome.utils.showGrayBtn
import kotlinx.android.synthetic.main.item_my_class.view.*

class MyParticipatedAdapter(list: ArrayList<ClassBean.Bean.Data>) : BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_my_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.txt_left.visibility = View.GONE
        holder.itemView.txt_mid.visibility = View.GONE
        holder.itemView.txt_right.visibility = View.GONE

        when (getData(position).order.state) {
            "1" -> {
                pay(holder.itemView.txt_right, position)
            }
            "2" -> {
                if (getData(position).course.state != "3") {
                    cancel(holder.itemView.txt_right, position)
                }
            }
            "3" -> {
                if (getData(position).course.coursetype != "4" && getData(position).course.coursetype != "5") {
                    cancel(holder.itemView.txt_right, position)
                }
            }
            "4" -> {
                if (getData(position).course.state == "4") {
                    cancel(holder.itemView.txt_right, position)
                }
            }
            "5" -> {

            }
        }

//        if (getData(position).course.state == "3"){
//            holder.itemView.txt_status.text = "发布者已取消"
//        }else{
        holder.itemView.txt_status.text = getData(position).course.stateInfo
//        }


        getData(position).materialList?.let {
            if (it.isNotEmpty()) {
                GlideUtil.loadPhoto(context, holder.itemView.img_class_photo, it[0].url ?: "")
            }
        }

        holder.itemView.txt_title.text = getData(position).course.title
        holder.itemView.txt_money.text = ("¥" + StringUtil.formatMoney((getData(position).course.sumPrice
                ?: "0").toDouble()))

        if (getData(position).course.coursetype == "4" || getData(position).course.coursetype == "5") {
            holder.itemView.txt_date.text = getData(position).course.worktime
        } else {
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


        holder.itemView.setOnClickListener {
            if (getData(position).course.coursetype == "4" || getData(position).course.coursetype == "5") {
                OrderDetailActivity.start(context, getData(position), false)
            } else {
                OrderDetailActivity.start(context, getData(position).course.id, false)
            }
        }
    }


    //取消
    private fun cancel(textView: TextView, position: Int) {
        textView.apply {
            showGrayBtn(this, "取消订单")
            setOnClickListener {
                var type = 2
                if (getData(position).course.coursetype == "4" || getData(position).course.coursetype == "5") {
                    type = 4
                }
                CancelPromptActivity.start(context, type, getData(position).course.id)
            }
        }
    }

    private fun publish(textView: TextView) {
        textView.apply {
            showBlueBtn(this, "再次发布")
            setOnClickListener {
                context.startActivity(Intent(context, CancelPromptActivity::class.java))

            }
        }
    }


    //支付
    private fun pay(textView: TextView, position: Int) {
        textView.apply {
            showBlueBtn(this, "去支付")
            setOnClickListener {
                PayActivity.start(context, getData(position), "")
            }
        }
    }

    private fun complete(textView: TextView) {
        textView.apply {
            showBlueBtn(this, "评价")
            setOnClickListener {

            }
        }
    }

}