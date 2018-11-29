package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.BannerBean
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_title.view.*

class HomeFragment : BaseFragment() {

    val bannerList = arrayListOf(
            BannerBean("测试1","http://img0.imgtn.bdimg.com/it/u=2626971423,1863586993&fm=26&gp=0.jpg"),
            BannerBean("测试2","http://bpic.588ku.com/back_pic/04/23/93/095835574167a48.jpg"),
            BannerBean("测试3","http://bpic.588ku.com/back_pic/05/33/76/075a27679aa8595.jpg"),
            BannerBean("测试4","http://bpic.588ku.com/back_pic/05/15/42/7659ad0545326ad.jpg")
    )


    private val classListFragment: ClassListFragment by lazy {
        ClassListFragment()
    }

    private val titleView: View by lazy {
        showTitle(titleManager.homeTitle())
    }

    override fun setLayout(): Int = R.layout.fragment_home

    override fun initView() {
        titleView.rlLocation.setOnClickListener(this)
        titleView.ivMessage.setOnClickListener(this)
        rl_banner.loadData(bannerList)
        childFragmentManager.beginTransaction().add(R.id.flContent, classListFragment).commit()
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