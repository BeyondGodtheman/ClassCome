package com.sunny.classcome.adapter

import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.InviteBean

class InviteAdapter(list: ArrayList<InviteBean.Bean.Data>): BaseRecycleAdapter<InviteBean.Bean.Data>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = View(context)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
    }
}