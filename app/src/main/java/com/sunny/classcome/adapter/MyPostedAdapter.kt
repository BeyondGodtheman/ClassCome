package com.sunny.classcome.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.activity.*
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import kotlinx.android.synthetic.main.item_my_class.view.*

class MyPostedAdapter(var baseActivity: BaseActivity,list: ArrayList<ClassBean.Bean.Data>) : BaseRecycleAdapter<ClassBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_my_class, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.txt_left.visibility = View.GONE
        holder.itemView.txt_mid.visibility = View.GONE
        holder.itemView.txt_right.visibility = View.GONE


        when (getData(position).course.state) {
            "1" -> {

            }
            "2" -> {
                if (getData(position).course.coursetype == "4" || getData(position).course.coursetype == "5") {
                    cancelTairn(holder.itemView.txt_mid, getData(position).course.id)
                    buy(holder.itemView.txt_right,getData(position).course.id)
                } else {
                    invite(holder.itemView.txt_left, getData(position).course.id)
                    applicants(holder.itemView.txt_mid, getData(position).course.id)
                    cancelOrder(holder.itemView.txt_right, getData(position).course.id)
                }
            }
            "3" -> {
//                //订单取消
                publishAgain(holder.itemView.txt_right,position)

            }
            "4" -> {
                cancelOrder(holder.itemView.txt_mid, getData(position).course.id)
                goPay(holder.itemView.txt_right, getData(position).course.id)

            }
            "5" -> {
                if (getData(position).course.coursetype == "4" || getData(position).course.coursetype == "5"){
                    cancelOrherOrder(holder.itemView.txt_mid, getData(position).course.id)
                    buy(holder.itemView.txt_right,getData(position).course.id)
                }else{
                    cancelOrder(holder.itemView.txt_mid, getData(position).course.id)
                    if (getData(position).course.state == "5"){
                        account(holder.itemView.txt_right,position)
                    }
                }
            }

        }

        holder.itemView.txt_status.text = getData(position).course.stateInfo
        getData(position).materialList?.let {
            if (it.isNotEmpty()) {
                GlideUtil.loadPhoto(context, holder.itemView.img_class_photo, it[0].url ?: "")
            }
        }

        holder.itemView.txt_title.text = getData(position).course.title
        holder.itemView.txt_money.text = ("¥" + StringUtil.formatMoney((getData(position).course.sumPrice
                ?: "0").toDouble()))

        if (getData(position).course.coursetype == "5"){
            holder.itemView.txt_date.text = getData(position).course.worktime
        }else{
            val timeSb = StringBuilder()

            getData(position).course.startTime?.let {
                timeSb.append(it.split(" ")[0])

            }
            timeSb.append("至")

            getData(position).course.endTime?.let {
                timeSb.append(it.split(" ")[0])

            }

            holder.itemView.txt_date.text = timeSb.toString()
        }


        holder.itemView.setOnClickListener {
            if (getData(position).course.coursetype == "4" || getData(position).course.coursetype == "5"){
                OrderDetailActivity.start(context,getData(position),true)
            }else{
                OrderDetailActivity.start(context, getData(position).course.id,true)
            }
        }
    }


    //取消
    private fun cancelOrder(textView: TextView, id: String) {
        textView.apply {
            showGrayBtn(this, "取消订单")
            setOnClickListener {
                CancelPromptActivity.start(context, 1, id)
            }
        }
    }

    //取消
    private fun cancelOrherOrder(textView: TextView, id: String) {
        textView.apply {
            showGrayBtn(this, "取消订单")
            setOnClickListener {
                CancelPromptActivity.start(context, 3, id)
            }
        }
    }


    //取消发布
    private fun cancelTairn(textView: TextView, id: String) {
        textView.apply {
            showGrayBtn(this, "取消发布")
            setOnClickListener {
                CancelPromptActivity.start(context, 1, id)
            }
        }
    }


    //去支付
    private fun goPay(textView: TextView, id: String) {
        textView.apply {
            showBlueBtn(this, "去支付")
            setOnClickListener {
                PayActivity.start(context, id)
            }
        }
    }

    //结算
    private fun account(textView: TextView,position: Int) {
        textView.apply {
            showBlueBtn(this, "结算")
            setOnClickListener {
                val params = hashMapOf<String, String>()
                params["courseId"] = getData(position).course.id
                params["useUserId"] = getData(position).course.winningBidder
                ApiManager.post(baseActivity.composites, params, Constant.ORDER_ACCOUNTSORDER, object : ApiManager.OnResult<BaseBean<String>>() {
                    override fun onSuccess(data: BaseBean<String>) {

                        ToastUtil.show(data.content?.info)
                        if (data.content?.statu == "1") {
                            getData(position).course.state = "6"
                            notifyDataSetChanged()
                        }

                    }

                    override fun onFailed(code: String, message: String) {

                    }

                })
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
    private fun publishAgain(textView: TextView,position: Int) {
        textView.apply {
            showBlueBtn(this, "再次发布")
            setOnClickListener {
                when(getData(position).course.coursetype){
                    "4" -> context.startActivity(Intent(context,PublishFieldActivity::class.java))
                    "5" -> context.startActivity(Intent(context,PublishTrainActivity::class.java))
                    else -> {
                        PublishClassActivity.start(context,getData(position).course.coursetype)
                    }
                }
            }
        }
    }

    private fun invite(textView: TextView, courseId: String) {
        textView.apply {
            showBlueBtn(this, "邀请用户")
            setOnClickListener {
                InviteActivity.start(context, courseId)
            }
        }
    }


    private fun buy(textView: TextView, courseId: String) {
        textView.apply {
            showBlueBtn(this, "购买者")
            setOnClickListener {
                BuyActivity.start(context, courseId)
            }
        }
    }



    private fun applicants(textView: TextView, courseId: String) {
        textView.apply {
            showBlueBtn(this, "应聘者")
            setOnClickListener {
                ApplicantsActivity.start(context, courseId)
            }
        }
    }

}