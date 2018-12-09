package com.sunny.classcome.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.activity.CancelPromptActivity
import com.sunny.classcome.activity.OrderDetailActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.showBlueBtn
import com.sunny.classcome.utils.showGrayBtn
import kotlinx.android.synthetic.main.item_my_class.view.*
import java.lang.StringBuilder

class MyPostedAdapter(list: ArrayList<ClassBean.Bean.Data>) : BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_my_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.txt_left.visibility = View.GONE
        holder.itemView.txt_mid.visibility = View.GONE
        holder.itemView.txt_right.visibility = View.GONE

        var orderDetailsType = 0

        val type = when (getData(position).course.state) {

            "-1" -> {

                "驳回"
            }
            "1" -> "待审核"
            "2" -> {
                cancel(holder.itemView.txt_left)
                invite(holder.itemView.txt_mid)
                applicants(holder.itemView.txt_right)
                orderDetailsType = OrderDetailActivity.order_audited
                "审核通过,未中标"
            }
            "3" -> {
                delete(holder.itemView.txt_mid)
                publishAgain(holder.itemView.txt_right)
                orderDetailsType = OrderDetailActivity.order_off_shelf
                "已取消"
            }
            "4" -> {
                cancel(holder.itemView.txt_mid)
                goPay(holder.itemView.txt_right)
                orderDetailsType = OrderDetailActivity.order_pay_wait
                "未付款"
            }
            "5" -> {
                cancel(holder.itemView.txt_mid)
                account(holder.itemView.txt_right)
                orderDetailsType = OrderDetailActivity.order_class_ing


                "进行中"
            }
            "6" -> "完成"
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

        holder.itemView.txt_date.text = timeSb.toString()

        holder.itemView.setOnClickListener {
            OrderDetailActivity.start(context, orderDetailsType, getData(position).course.id)
        }
    }


    //取消
    private fun cancel(textView: TextView) {
        textView.apply {
            showGrayBtn(this, "取消订单")
            setOnClickListener {
                context.startActivity(Intent(context, CancelPromptActivity::class.java))

            }
        }
    }

    //去支付
    private fun goPay(textView: TextView) {
        textView.apply {
            showBlueBtn(this, "去支付")
            setOnClickListener {

            }
        }
    }

    //结算
    private fun account(textView: TextView) {
        textView.apply {
            showBlueBtn(this, "结算")
            setOnClickListener {

            }
        }
    }

    //删除
    private fun delete(textView: TextView) {
        textView.apply {
            showGrayBtn(this, "删除")
            setOnClickListener {
                context.startActivity(Intent(context, CancelPromptActivity::class.java))

            }
        }
    }

    //再次发布
    private fun publishAgain(textView: TextView) {
        textView.apply {
            showGrayBtn(this, "再次发布")
            setOnClickListener {

            }
        }
    }

    private fun invite(textView: TextView) {
        textView.apply {
            showBlueBtn(this, "邀请用户")
            setOnClickListener {

            }
        }
    }

    private fun applicants(textView: TextView) {
        textView.apply {
            showBlueBtn(this, "应聘者")
            setOnClickListener {

            }
        }
    }

}