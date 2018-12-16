package com.sunny.classcome.adapter

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.item_class_type.view.*

class ClassTypeAdapter(list:ArrayList<ClassTypeBean>,var selectMap:HashMap<String,ArrayList<ClassTypeBean.SubCategory>>): BaseRecycleAdapter<ClassTypeBean>(list) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_class_type,parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.txt_type.text = getData(position).name
        holder.itemView.recl.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = GridLayoutManager(context,4)
            adapter = ClassTypeChildAdapter(getData(position).subCategoryList,selectMap,getData(position).id).apply {
                setOnItemClickListener { _, index ->
                    if (selectMap.isEmpty()){
                        selectMap[this@ClassTypeAdapter.getData(position).id] = arrayListOf(getData(index))
                        notifyDataSetChanged()
                    }else{
                        if (selectMap.containsKey(this@ClassTypeAdapter.getData(position).id)){
                            selectMap[this@ClassTypeAdapter.getData(position).id]?.let {
                                if (!it.contains(getData(index))){
                                    it.add(getData(index))
                                }else{
                                    it.remove(getData(index))
                                    if (it.isEmpty()){
                                        selectMap.remove(this@ClassTypeAdapter.getData(position).id)
                                    }
                                }
                                notifyDataSetChanged()
                            }
                        }else{
                            ToastUtil.show("课程分类只能选择一种大分类")
                        }
                    }
                }
            }
        }
    }
}