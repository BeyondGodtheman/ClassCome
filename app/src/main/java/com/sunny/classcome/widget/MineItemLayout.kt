package com.sunny.classcome.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.item_mine.view.*

/**
 * Desc 个人中心子条目自定义布局
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/11/30 23:02
 */
class MineItemLayout : RelativeLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    fun initView(attrs: AttributeSet?) {

        setBackgroundResource(R.color.color_white)

        LayoutInflater.from(context).inflate(R.layout.item_mine, this, true)

        val styleable = context.obtainStyledAttributes(attrs, R.styleable.MineItemLayout)
        val resId = styleable.getResourceId(R.styleable.MineItemLayout_mine_layout_icon, 0)
        if (resId == 0){
            img_icon.visibility = View.GONE
        }else{
            img_icon.setImageResource(resId)
        }
        txt_info.text = styleable.getString(R.styleable.MineItemLayout_mine_layout_text)

        styleable.recycle()
    }


}