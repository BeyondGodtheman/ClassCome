package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ChequesDetailBean
import com.sunny.classcome.utils.DateUtil
import kotlinx.android.synthetic.main.item_cheque_detail.view.*

/**
 * Desc 我的收款明细
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:28
 */
class MyChequesDetailAdapter(list: ArrayList<ChequesDetailBean.Bean.Data>) : BaseRecycleAdapter<ChequesDetailBean.Bean.Data>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
            LayoutInflater.from(context).inflate(R.layout.item_cheque_detail, parent, false)


    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        list[position].apply {
            holder.itemView.txt_type.text = remarks
            holder.itemView.txt_date.text = DateUtil.dateFormatYYMMddHHssmm(createTime?:"")
            holder.itemView.txt_balance.text = ("余额：${sumMoney?:"0.00"}")
            holder.itemView.txt_money.text = ((if (payType == "1") "+" else "-") + (paymentMoney?:"0.00"))
        }
    }

}