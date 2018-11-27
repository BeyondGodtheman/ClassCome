package com.sunny.classcome.utils

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.layout_default_title.view.*
import kotlinx.android.synthetic.main.layout_home_title.view.*

class TitleManager(var activity: Activity?) {

    fun defaultTitle(title: String): View {
        val view = View.inflate(activity, R.layout.layout_default_title, null)
        view.tvTitle.text = title
        view.rlLeft.setOnClickListener {
            activity?.finish()
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