package com.sunny.classcome.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.JourneyBean
import com.sunny.classcome.utils.GlideUtil
import kotlinx.android.synthetic.main.item_itinerary_desc.view.*

class ItineraryDescAdapter(list:ArrayList<JourneyBean.Bean.Entity>):BaseRecycleAdapter<JourneyBean.Bean.Entity>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_itinerary_desc,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        getData(position).let {it ->

                GlideUtil.loadPhoto(context,holder.itemView.img_photo,it.url)
                holder.itemView.txt_time.text = it.time
                holder.itemView.txt_title.text = it.title

                holder.itemView.setOnClickListener {
                    OrderDetailActivity.start(context,getData(position).courseId,false)
                }

        }

    }
}