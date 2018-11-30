package com.sunny.classcome.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.sunny.classcome.R

/**
 * Desc 个人中心子条目自定义布局
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/11/30 23:02
 */
class MineItemLayout : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.item_publish, this, true)
    }





}