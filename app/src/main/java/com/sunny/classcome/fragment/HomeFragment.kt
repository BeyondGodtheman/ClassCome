package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_base.*

class HomeFragment : BaseFragment() {
    override fun setLayout(): Int = R.layout.fragment_home

    override fun initView() {
        showTitle(titleManager.homeTitle())
    }

    override fun onClick(v: View?) {
    }
}