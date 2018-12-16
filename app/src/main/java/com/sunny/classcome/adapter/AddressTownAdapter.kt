package com.sunny.classcome.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.LocalCityBean
import kotlinx.android.synthetic.main.item_location_city.view.*

class AddressTownAdapter(list: ArrayList<LocalCityBean.City.County.Town>): BaseRecycleAdapter<LocalCityBean.City.County.Town>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_location_city,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_location_city.text = getData(position).townName
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white))
    }
}