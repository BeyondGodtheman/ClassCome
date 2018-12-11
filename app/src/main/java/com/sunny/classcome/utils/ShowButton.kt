package com.sunny.classcome.utils

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R

/**
 * Desc 显示不同背景的按钮
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/6 00:14
 */

fun showBlueBtn(txt: TextView, str: String) {
    txt.visibility = View.VISIBLE
    if (str.isNotEmpty()){
        txt.text = str
    }
    txt.setBackgroundResource(R.drawable.draw_bg_fillet_blue_border)
    txt.setTextColor(ContextCompat.getColor(MyApplication.getApp().applicationContext, R.color.color_nav_blue))
}

fun showGrayBtn(txt: TextView, str: String) {
    txt.visibility = View.VISIBLE
    if (str.isNotEmpty()){
        txt.text = str
    }
    txt.setBackgroundResource(R.drawable.draw_bg_fillet_gray_border)
    txt.setTextColor(ContextCompat.getColor(MyApplication.getApp().applicationContext, R.color.color_gray_font))
}