package com.sunny.classcome.widget.popup

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.BaseRecycleAdapter
import com.sunny.classcome.base.BaseRecycleViewHolder
import com.sunny.classcome.utils.DensityUtil
import kotlinx.android.synthetic.main.item_dialog_select.view.*
import kotlinx.android.synthetic.main.popup_money.view.*

class MoneyPopup(var context: BaseActivity, var list: ArrayList<String>, action: (Int) -> Unit) : PopupWindow(context) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        contentView = inflater.inflate(R.layout.popup_money, null, false)

        width = WindowManager.LayoutParams.MATCH_PARENT

        setBackgroundDrawable(ColorDrawable(0))
        isFocusable = true
        isOutsideTouchable = true
        contentView.recl.layoutManager = LinearLayoutManager(context)
        contentView.recl.adapter = SelectDialogAdapter(list).apply {
            setOnItemClickListener { _, index ->
                action(index)
                dismiss()
            }
        }
    }


    class SelectDialogAdapter(list: ArrayList<String>) : BaseRecycleAdapter<String>(list) {
        override fun setLayout(parent: ViewGroup, viewType: Int): View = LayoutInflater.from(context).inflate(R.layout.item_dialog_select, parent, false)

        override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
            holder.itemView.txt_name.text = getData(position)
        }

    }

    override fun showAsDropDown(anchor: View?) {
        height = DensityUtil.appHeight() - DensityUtil.getStatusHeight() - (anchor?.bottom?:0)
        super.showAsDropDown(anchor)
    }
}