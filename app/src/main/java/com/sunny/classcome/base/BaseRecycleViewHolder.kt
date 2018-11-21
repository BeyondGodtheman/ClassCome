package com.sunny.classcome.base

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

@Suppress("UNCHECKED_CAST")
class BaseRecycleViewHolder(itemView:View, var onItemClickListener:((view: View,index:Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val viewMap = SparseArray<View>()

    init {
        if (onItemClickListener != null) {
            itemView.setOnClickListener(this)
        }
    }

    fun <T : View> getView(id: Int): T {
        if (viewMap.get(id) != null) {
            return viewMap.get(id) as T
        }
        val view = itemView.findViewById(id) as T
        viewMap.put(id, view)
        return view
    }

    override fun onClick(v: View) {
        onItemClickListener?.invoke(v, adapterPosition)
    }
}