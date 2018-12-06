package com.sunny.classcome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.base.LevelDesc
import kotlinx.android.synthetic.main.item_leve_desc.view.*

class LevelDescAdapter(list:ArrayList<LevelDesc>):BaseRecycleAdapter<LevelDesc>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_leve_desc,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_level.text = getData(position).gradeName
        holder.itemView.txt_count.text = getData(position).score
    }
}