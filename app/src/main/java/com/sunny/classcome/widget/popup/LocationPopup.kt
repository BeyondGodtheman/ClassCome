package com.sunny.classcome.widget.popup

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.PopupWindow
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.popup_location.view.*

class LocationPopup(context: Context) : PopupWindow(context) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        contentView = inflater.inflate(R.layout.popup_location,null)
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = context.resources.getDimension(R.dimen.pt500).toInt()
        setBackgroundDrawable(ColorDrawable(0))
        isFocusable = true
        isOutsideTouchable = true
        contentView.recl_city.layoutManager = LinearLayoutManager(context)
    }

}