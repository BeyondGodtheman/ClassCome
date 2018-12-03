package com.sunny.classcome.utils

import android.app.Activity
import android.view.View
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.layout_default_title.view.*
import kotlinx.android.synthetic.main.layout_home_title.view.*

class TitleManager(var activity: Activity?) {

    fun defaultTitle(title: String): View = defaultTitle(title, true, "", null)

    fun defaultTitle(title: String, rightText: String, rightOnClickListener: View.OnClickListener?): View = defaultTitle(title, true, rightText, rightOnClickListener)

    fun defaultTitle(title: String, isShowIcon: Boolean, rightText: String, rightOnClickListener: View.OnClickListener?): View {
        val view = View.inflate(activity, R.layout.layout_default_title, null)
        view.txt_title.text = title

        if (!isShowIcon) {
            view.ll_back.visibility = View.GONE
        } else {
            view.ll_back.setOnClickListener {
                activity?.finish()
            }
        }

        view.txt_right.text = rightText

        rightOnClickListener?.let {
            view.txt_right.setOnClickListener(rightOnClickListener)
        }

        return view
    }

    fun homeTitle(): View {
        return View.inflate(activity, R.layout.layout_home_title, null)
    }


    fun onDestroy() {
        activity = null
    }

}