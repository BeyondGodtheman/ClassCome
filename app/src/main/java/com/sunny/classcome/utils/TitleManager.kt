package com.sunny.classcome.utils

import android.app.Activity
import android.view.View
import com.sunny.classcome.R
import kotlinx.android.synthetic.main.layout_default_title.view.*
import kotlinx.android.synthetic.main.layout_title_arrow.view.*
import kotlinx.android.synthetic.main.layout_title_icon.view.*
import kotlinx.android.synthetic.main.layout_title_search.view.*

class TitleManager(var activity: Activity?) {

    fun defaultTitle(title: String): View = defaultTitle(title, true, "", null)

    fun defaultTitle(title: String, rightText: String, rightOnClickListener: View.OnClickListener?): View = defaultTitle(title, true, rightText, rightOnClickListener)

    fun defaultTitle(title: String, isShowIcon: Boolean, rightText: String, rightOnClickListener: View.OnClickListener?): View {
        val view = View.inflate(activity, R.layout.layout_default_title, null)
        view.txt_base_title.text = title

        if (!isShowIcon) {
            view.ll_base_back.visibility = View.GONE
        } else {
            view.ll_base_back.setOnClickListener {
                activity?.finishAfterTransition()
            }
        }

        view.txt_base_right.text = rightText

        rightOnClickListener?.let {
            view.txt_base_right.setOnClickListener(rightOnClickListener)
        }

        return view
    }

    fun homeTitle(): View {
        return View.inflate(activity, R.layout.layout_home_title, null)
    }


    fun arrowTitle(title: String,titleOnClickListener: View.OnClickListener?):View{
        val view = View.inflate(activity, R.layout.layout_title_arrow, null)
        view.txt_arrow_title.text = title


            view.ll_arrow_back.setOnClickListener {
                activity?.finishAfterTransition()
            }

        titleOnClickListener?.let {
            view.txt_arrow_title.setOnClickListener(titleOnClickListener)
        }

        return view
    }

    fun iconTitle(title: String,titleOnClickListener: View.OnClickListener?):View{
        val view = View.inflate(activity, R.layout.layout_title_icon, null)
        view.txt_icon_title.text = title
        view.ll_icon_back.setOnClickListener {
            activity?.finishAfterTransition()
        }

        titleOnClickListener?.let {
            view.view_icon_right.setOnClickListener(titleOnClickListener)
        }

        return view
    }

    fun searchTitle(keyWord:String,action:(keyWord:String)-> Unit):View{
        val view = View.inflate(activity, R.layout.layout_title_search, null)
        view.edit_search_keyword.setText(keyWord)
        view.ll_search_back.setOnClickListener {
            activity?.finishAfterTransition()
        }
        view.txt_search.setOnClickListener{
                action(view.edit_search_keyword.text.toString())
            }

        return view
    }


    fun onDestroy() {
        activity = null
    }

}