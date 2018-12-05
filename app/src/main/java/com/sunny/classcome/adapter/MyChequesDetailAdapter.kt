package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ChequesDetailBean
import kotlinx.android.synthetic.main.item_cheque_detail.view.*

/**
 * Desc 我的收款明细
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:28
 */
class MyChequesDetailAdapter(list: ArrayList<ChequesDetailBean>) : BaseRecycleAdapter<ChequesDetailBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
            LayoutInflater.from(context).inflate(R.layout.item_cheque_detail, parent, false)


    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        list[position].apply {
            holder.itemView.txt_type.text = type
            holder.itemView.txt_date.text = date
            holder.itemView.txt_balance.text = balance
            holder.itemView.txt_money.text = money
        }
    }

}