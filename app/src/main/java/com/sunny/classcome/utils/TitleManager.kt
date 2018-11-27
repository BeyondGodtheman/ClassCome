package com.sunny.classcome.utils

import android.view.View
import android.widget.FrameLayout
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.layout_default_title.view.*
import kotlinx.android.synthetic.main.layout_home_title.view.*

class TitleManager {

    fun defaultTitle(titleLayout: FrameLayout, title: String): View {
        val view = View.inflate(titleLayout.context, R.layout.layout_default_title, null)
        view.tvTitle.text = title
        addView(titleLayout,view)
        return view
    }

    fun homeTitle(titleLayout: FrameLayout):View{
        val view = View.inflate(titleLayout.context, R.layout.layout_home_title, null)
        addView(titleLayout,view)
        return view
    }


    private fun addView(titleLayout: FrameLayout,view:View){
        titleLayout.removeAllViews()
        titleLayout.addView(view)
    }


}