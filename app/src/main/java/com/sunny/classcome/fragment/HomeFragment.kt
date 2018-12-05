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
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.LocalCityBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.LocationUtil
import com.sunny.classcome.utils.SharedUtil
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_recommend_text.view.*
import kotlinx.android.synthetic.main.layout_home_title.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class HomeFragment : BaseFragment() {

    private var isShowLocal = false
    private var localStr = listOf<String>()

    private val classListFragment: ClassListFragment by lazy {
        ClassListFragment()
    }

    private val locationUtil: LocationUtil by lazy {
        LocationUtil(requireContext()) {
            if (isShowLocal) {
                titleView.text_home_Location.text = localStr[1]
            } else {
                titleView.text_home_Location.text = it
                loadAddress()
            }
        }
    }

    private val titleView: View by lazy {
        showTitle(titleManager.homeTitle())
    }

    override fun setLayout(): Int = R.layout.fragment_home

    override fun initView() {

        localStr = UserManger.getAddress().apply {
            if (isNotEmpty()) {
                isShowLocal = true
                launch(UI){
                    delay(100)
                    classListFragment.loadClass()
                }
            }
        }.split(",")

        titleView.rlLocation.setOnClickListener(this)
        titleView.ivMessage.setOnClickListener(this)

        loadBanner()
        initRecommend()
        childFragmentManager.beginTransaction().add(R.id.flContent, classListFragment).commit()

        locationUtil.startLocation()
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.rlLocation -> startActivityForResult(Intent(context, LocationActivity::class.java), 1)
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


    private fun loadBanner() {
        ApiManager.post(getBaseActivity().composites, null, Constant.COURSE_GETIMAGEOFPAGE, object : ApiManager.OnResult<BannerBean>() {
            override fun onSuccess(data: BannerBean) {
                rl_banner.loadData(data.content)
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }

    private fun loadAddress() {
        ApiManager.post(getBaseActivity().composites, null, Constant.PUB_GETCITYLIST, object : ApiManager.OnResult<LocalCityBean>() {
            override fun onSuccess(data: LocalCityBean) {
                data.content.first { it.cityVoName == titleView.text_home_Location.text.toString() }.let {
                    UserManger.setAddress(it.cityVoId, it.cityVoName)
                    classListFragment.loadClass()
                }
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 1) {
            UserManger.getAddress().apply {
                localStr = split(",")
                isShowLocal = true
                titleView.text_home_Location.text = localStr[1]
                classListFragment.loadClass()
            }
        }
    }
}