package com.sunny.classcome.utils

import android.view.View
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.layout_default_title.view.*

class TitleManager {

    fun defaultTitle(titleBody: View, title: String): View {
        val view = View.inflate(titleBody.context, R.layout.layout_default_title, null)
        view.tvTitle.text = title
        return view
    }
}