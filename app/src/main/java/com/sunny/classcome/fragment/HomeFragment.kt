package com.sunny.classcome.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.LocationActivity
import com.sunny.classcome.activity.LoginActivity
import com.sunny.classcome.activity.MyMsgActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.BannerBean
import com.sunny.classcome.utils.LocationUtil
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_recommend_text.view.*
import kotlinx.android.synthetic.main.layout_home_title.view.*

class HomeFragment : BaseFragment() {

    private val bannerList = arrayListOf(
            BannerBean("测试1", "http://img0.imgtn.bdimg.com/it/u=2626971423,1863586993&fm=26&gp=0.jpg"),
            BannerBean("测试2", "http://bpic.588ku.com/back_pic/04/23/93/095835574167a48.jpg"),
            BannerBean("测试3", "http://bpic.588ku.com/back_pic/05/33/76/075a27679aa8595.jpg"),
            BannerBean("测试4", "http://bpic.588ku.com/back_pic/05/15/42/7659ad0545326ad.jpg")
    )


    private val classListFragment: ClassListFragment by lazy {
        ClassListFragment()
    }

    private val locationUtil: LocationUtil by lazy {
        LocationUtil(requireContext()) {
            titleView.text_home_Location.text = it
        }
    }

    private val titleView: View by lazy {
        showTitle(titleManager.homeTitle())
    }

    override fun setLayout(): Int = R.layout.fragment_home

    override fun initView() {

        locationUtil.startLocation()

        titleView.rlLocation.setOnClickListener(this)
        titleView.ivMessage.setOnClickListener(this)
        rl_banner.loadData(bannerList)
        initRecommend()
        childFragmentManager.beginTransaction().add(R.id.flContent, classListFragment).commit()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.rlLocation -> startActivity(Intent(context, LocationActivity::class.java))
            R.id.ivMessage -> {
                if (UserManger.isLogin()) {
                    startActivity(Intent(context, MyMsgActivity::class.java))
                } else {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }

        }

    }

    //初始化推荐数据
    private fun initRecommend() {
        val recommendList = arrayListOf(
                "广告主向广告受众，所传达的内容，即广告信源通过广告媒介，向广告信宿传达的内容，该内容形式多样",
                "生活片断。例如，高博特盐水瓶的广告表现一家人在日常生活中正在使用本产品",
                "音乐。例如，可口可乐广告，它的广告歌曲歌词反复强调产品和品牌名称。",
                "幻想。例如，蔓登琳冷饮广告，设计出一种幻想境界。",
                "气氛和形象。例如，\"蔓陀思\"果汁糖，引起人们联想某种轻松气氛和形象，给人以暗示，但不对产品性能做任何直接宣传。"
        )

        recommendList.forEach {
            val layoutView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_home_recommend_text, vf_home_commend, false)
            layoutView.txt_title.text = it
            vf_home_commend.addView(layoutView)
        }
        vf_home_commend.startFlipping()
    }

}