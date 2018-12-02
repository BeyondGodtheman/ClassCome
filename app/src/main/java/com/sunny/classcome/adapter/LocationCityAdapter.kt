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

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/2 00:11
 */
class LocationCityAdapter(mList: ArrayList<LocalCityBean>) : BaseRecycleAdapter<LocalCityBean>(mList) {



    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_location_city, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        if (getData(position).pinyin.length == 1) {
            holder.itemView.view_location_line.visibility = View.GONE
            holder.itemView.txt_location_city.setTextColor(ContextCompat.getColor(context, R.color.color_gray_font))
        } else {
            holder.itemView.view_location_line.visibility = View.VISIBLE
            holder.itemView.txt_location_city.setTextColor(ContextCompat.getColor(context, R.color.color_default_font))
        }
        holder.itemView.txt_location_city.text = getData(position).cityName
    }
}