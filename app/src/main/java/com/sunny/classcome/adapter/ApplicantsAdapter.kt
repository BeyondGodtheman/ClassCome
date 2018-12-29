package com.sunny.classcome.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ApplicantsBean
import com.sunny.classcome.utils.DateUtil
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.showBlueBtn
import com.sunny.classcome.utils.showGrayBtn
import kotlinx.android.synthetic.main.item_applicants.view.*

class ApplicantsAdapter(list:ArrayList<ApplicantsBean.Bean.Data>,var applicantsOption:ApplicantsOption): BaseRecycleAdapter<ApplicantsBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_applicants,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        GlideUtil.loadHead(context,holder.itemView.img_user_head,getData(position).userPic?:"")
        holder.itemView.txt_name.text = ((getData(position).userName?:"")+"对您的课程发起了应聘")
        holder.itemView.txt_time.text = DateUtil.dateFormatYYMMddHHssmm(getData(position).createTime?:"")

        if (getData(position).state == "1"){
            holder.itemView.txt_status.text = ""
            showBlueBtn(holder.itemView.btn_left,"候选")
            holder.itemView.btn_left.visibility = View.VISIBLE
            holder.itemView.btn_right.visibility = View.VISIBLE
        }

        if (getData(position).state == "2"){
            holder.itemView.txt_status.setTextColor(ContextCompat.getColor(context,R.color.color_default_font))
            holder.itemView.txt_status.text = "进入候选"
            showGrayBtn(holder.itemView.btn_left,"取消候选")
            holder.itemView.btn_left.visibility = View.VISIBLE
            holder.itemView.btn_right.visibility = View.VISIBLE
        }

        if (getData(position).state == "3"){
            holder.itemView.txt_status.setTextColor(ContextCompat.getColor(context,R.color.color_price_red))
            holder.itemView.txt_status.text = "已中标"
            holder.itemView.btn_left.visibility = View.GONE
            holder.itemView.btn_right.visibility = View.GONE
        }

        holder.itemView.btn_left.setOnClickListener {
            if (getData(position).state == "1"){
                applicantsOption.candidate(position)
            }else{
                applicantsOption.cancelCandidate(position)
            }
        }

        holder.itemView.btn_right.setOnClickListener {
            applicantsOption.winningBid(position)
        }

    }

    interface ApplicantsOption{

        //候选操作
        fun candidate(position: Int)

        //取消候选
        fun cancelCandidate(position: Int)

        //中标操作
        fun winningBid(position: Int)
    }
}