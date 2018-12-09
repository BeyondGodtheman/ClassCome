package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.MsgBean
import com.sunny.classcome.utils.DateUtil
import kotlinx.android.synthetic.main.item_my_msg.view.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 14:41
 */
class MyMsgAdapter(list: ArrayList<MsgBean.Content.Bean.Data>) : BaseRecycleAdapter<MsgBean.Content.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_my_msg, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_msg_time.text = DateUtil.dateFormatYYMMddHHssmm(getData(position).createTime)
        holder.itemView.txt_msg_title.text = getData(position).title
        holder.itemView.txt_msg_desc.text = getData(position).message
    }
}