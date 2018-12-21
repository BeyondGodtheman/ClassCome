package com.sunny.classcome.adapter

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassTypeBean
import kotlinx.android.synthetic.main.item_class_type.view.*

class TranTypeAdapter(list: ArrayList<ClassTypeBean>, var selectSet: HashSet<String>) : BaseRecycleAdapter<ClassTypeBean>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_class_type, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_type.text = getData(position).name
        holder.itemView.recl.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = GridLayoutManager(context, 4)
            adapter = TranTypeChildAdapter(getData(position).subCategoryList,selectSet).apply {
                setOnItemClickListener { _, index ->
                    if (selectSet.contains(getData(index).name)) {
                        selectSet.remove(getData(index).name)
                    } else {
                        selectSet.add(getData(index).name)
                    }
                    this@TranTypeAdapter.notifyDataSetChanged()
                }
            }

        }
    }
}