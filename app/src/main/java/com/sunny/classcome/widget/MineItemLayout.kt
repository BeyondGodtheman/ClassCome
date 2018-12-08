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
 * 支持以下布局样式：
 *      1、img-txt-（img_more）
 *      2、txt-（img_more）
 *      3、txt-txt-（img_more）
 *      4、txt-txt-txt-（img_more）
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

        // 设置icon
        val resId = styleable.getResourceId(R.styleable.MineItemLayout_mine_layout_icon, 0)
        if (resId == 0) {
            img_icon.visibility = View.GONE
        } else {
            img_icon.setImageResource(resId)
        }

        txt_info_left.text = styleable.getString(R.styleable.MineItemLayout_mine_layout_text_left)
        txt_info_right.text = styleable.getString(R.styleable.MineItemLayout_mine_layout_text_right)

        // 是否显示默认按钮（选择支付方式显示）
        val showDefault = styleable.getBoolean(R.styleable.MineItemLayout_mine_layout_text_show_default, false)
        txt_default.visibility = if (showDefault) View.VISIBLE else View.GONE

        styleable.recycle()
    }


    fun setRightText(text: String) {
        txt_info_right.text = text
    }


    fun setDeafult(visibility:Int){
        txt_default.visibility = visibility
    }
}