package com.sunny.classcome.widget.button

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.sunny.classcome.R
import java.util.*

/**
 * 带计时器的button
 * Created by zhangye on 2018/1/25.
 */
class TimerButton : TextView, View.OnClickListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val defaultSecond = 60
    private var second = 0
    private var isClick = false
    private var isShow = false
    private val timer = Timer()
    private var listener: View.OnClickListener? = null

    private val mHandler = Handler(Handler.Callback {
        if (second <= 0) {
            if (isShow) {
                setBackgroundResource(R.drawable.draw_bg_fillet_blue)
            } else {
                setBackgroundResource(R.drawable.draw_btn_fillet_gray)
            }

            isClick = false

            text = resources.getString(R.string.get_code)
        } else {

            text = ("${second}s")
            setBackgroundResource(R.drawable.draw_btn_fillet_gray)
        }

        second--

        return@Callback false
    })


    init {

        setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (isClick || !isShow) {
            return
        }
        listener?.onClick(v)

    }

    //根据环境设置按钮是否可以点击
    fun setClick(isShow: Boolean) {
        this.isShow = isShow

        if (isShow && !isClick) {
            setBackgroundResource(R.drawable.draw_bg_fillet_blue)
        } else {
            setBackgroundResource(R.drawable.draw_btn_fillet_gray)
        }
    }

    fun setCallListener(listener: OnClickListener) {
        this.listener = listener
    }

    fun action() {
        isClick = true

        second = defaultSecond

        val timerTask = object : TimerTask() {
            override fun run() {

                mHandler.sendEmptyMessage(second)

                if (second <= 0) {
                    cancel()
                }
            }
        }
        timer.schedule(timerTask, 0, 1000)
    }
}