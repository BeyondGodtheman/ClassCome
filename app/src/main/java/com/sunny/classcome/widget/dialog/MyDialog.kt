package com.sunny.classcome.widget.dialog

import android.app.Dialog
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.layout_dialog_default.*

/**
 * 首媒默认样式的对话框
 * Created by zhangye on 2018/1/27.
 */
class MyDialog(context: Context) : Dialog(context), View.OnClickListener {
    var onClickListener1: View.OnClickListener? = null
    var cancelOnClickListener: View.OnClickListener? = null

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_dialog_default)
        tvCancel.setOnClickListener(this)
        tvSure.setOnClickListener(this)
        setCanceledOnTouchOutside(false)
        window?.attributes?.width = context.resources.getDimension(R.dimen.pt570).toInt()
        window?.setBackgroundDrawableResource(R.color.color_transparent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            tvCancel.id -> {
                cancelOnClickListener?.onClick(v)
                dismiss()
            }
            tvSure.id -> {
                onClickListener1?.onClick(v)
            }
        }
    }

    override fun setTitle(title: CharSequence?) {
        txt_title.text = title
    }

    fun setDesc(desc: String) {
        tvDesc.visibility = View.VISIBLE
        tvDesc.text = desc
    }

    fun setDescColor(color: Int) {
        tvDesc.setTextColor(ContextCompat.getColor(context,color))
    }


    fun setPositiveText(posiText: String) {
        tvSure.text = posiText
    }

    fun setCancelText(posiText: String) {
        tvCancel.text = posiText
    }

    fun singleButton() {
        tvCancel.visibility = View.GONE
    }


    override fun onBackPressed() {
    }
}
