package com.sunny.classcome.widget.dialog

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import kotlinx.android.synthetic.main.item_dialog_select.view.*

class SelectDialog(context: Context,var onResult:(id:String)->Unit) : Dialog(context) {

    private val list = arrayListOf("家教","代课","活动","场地","培训")

    private val ids = arrayListOf("1","2","3","4","5")

   private var recyclerView:RecyclerView

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        recyclerView = RecyclerView(context)
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SelectDialogAdapter(list).apply {
            setOnItemClickListener { _, index ->
                onResult(ids[index])
                dismiss()
            }
        }
        setContentView(recyclerView)
        window?.attributes?.width = context.resources.getDimension(R.dimen.pt570).toInt()
        window?.setBackgroundDrawableResource(R.color.color_transparent)
    }


    class SelectDialogAdapter(list: ArrayList<String>):BaseRecycleAdapter<String>(list){
        override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_dialog_select,parent,false)

        override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
            holder.itemView.txt_name.text = getData(position)
        }

    }

}