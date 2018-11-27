package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.layout_home_title.view.*

class HomeFragment : BaseFragment() {
    private val titleView: View by lazy {
        showTitle(titleManager.homeTitle())
    }

    override fun setLayout(): Int = R.layout.fragment_home

    override fun initView() {
        titleView.rlLocation.setOnClickListener(this)
        titleView.ivMessage.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlLocation -> {

            }
            R.id.ivMessage -> {

            }

        }

    }
}