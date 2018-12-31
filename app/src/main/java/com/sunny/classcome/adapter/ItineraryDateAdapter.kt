package com.sunny.classcome.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ItineraryDateBean
import kotlinx.android.synthetic.main.item_itinerary_date.view.*
import java.util.*

class ItineraryDateAdapter(list: ArrayList<ItineraryDateBean>) : BaseRecycleAdapter<ItineraryDateBean>(list) {

    private var slDate = ""

    private val tomorrow: String by lazy {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthStr = (if (month < 10) "0" else "") + month
        val dayStr = (if (day < 10) "0" else "") + day
        ("${calendar.get(Calendar.YEAR)}-$monthStr-$dayStr")
    }

    private val today: String by lazy {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthStr = (if (month < 10) "0" else "") + month
        val dayStr = (if (day < 10) "0" else "") + day
        ("${calendar.get(Calendar.YEAR)}-$monthStr-$dayStr")
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_itinerary_date, parent, false)
    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        when (getData(position).date) {
            today -> holder.itemView.txt_title.text = "今天"
            tomorrow -> holder.itemView.txt_title.text = "明天"
            else -> holder.itemView.txt_title.text = getData(position).title
        }

        if (slDate == getData(position).date){
            holder.itemView.rl_bg.setBackgroundResource(R.color.color_white)
            holder.itemView.txt_title.setTextColor(ContextCompat.getColor(context,R.color.color_nav_blue))
            holder.itemView.txt_week.setTextColor(ContextCompat.getColor(context,R.color.color_nav_blue))
        }else{
            holder.itemView.rl_bg.setBackgroundColor(Color.parseColor("#F2F2F2"))
            holder.itemView.txt_title.setTextColor(ContextCompat.getColor(context,R.color.color_default_font))
            holder.itemView.txt_week.setTextColor(ContextCompat.getColor(context,R.color.color_gray_font))
        }

        val week = when (getData(position).week) {
            1 -> "一"
            2 -> "二"
            3 -> "三"
            4 -> "四"
            5 -> "五"
            6 -> "六"
            0 -> "日"
            else -> toString()
        }
        holder.itemView.txt_week.text = ("周$week")

    }

    fun select(position: Int){
        slDate = getData(position).date
        notifyDataSetChanged()
    }
}