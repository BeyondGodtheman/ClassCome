package com.sunny.classcome.utils

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R

@SuppressLint("ShowToast")
/**
 * 功能:单例Toast
 * 创建时间: 2016/9/12/03:18
 * Created by 张野 on 2017/10/12.
 */
object ToastUtil {

    private val toast:Toast by lazy {
        Toast.makeText(MyApplication.getApp(), "", Toast.LENGTH_SHORT).apply {
            val textView = (view as LinearLayout).getChildAt(0) as TextView
            val layoutParams =  textView.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = textView.resources.getDimension(R.dimen.dp325).toInt()
            textView.layoutParams = layoutParams
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textView.resources.getDimension(R.dimen.dp26))
        }
    }
    /**
     * 显示Toast
     * @param content Toast信息
     */
    fun show(content: String?, type: Int) {
        content?.let {
            toast.setText(content)
            toast.duration = type
            toast.show()
        }
    }

    fun show(content: String?) {
        show(content, Toast.LENGTH_SHORT)
    }

}