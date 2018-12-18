package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.activity.MapActivity
import com.sunny.classcome.activity.SplashActivity
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.fragment_field_details.*
import kotlinx.android.synthetic.main.fragment_train_details.*
import kotlinx.android.synthetic.main.layout_train_details.*

/**
 * Desc 培训详情,比场地详情多了一个拼单功能
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/15 20:40
 */
class TrainDetailsFragment : FieldDetailsFragment() {

    private var lan = "0"
    private var lon = "0"
    private var name = ""

    override fun initView() {
        super.initView()

        layout_pin.visibility = View.VISIBLE
        txt_long_desc.text = "培训时长"
        txt_time_desc.text = "培训时间"

        MyApplication.getApp().getData<ClassDetailBean>(Constant.CLASS_DETAIL, true).let { bean ->

            lan = bean?.content?.resCourseVO?.latitude?:"0"

            lon = bean?.content?.resCourseVO?.longitude?:"0"

            name = bean?.content?.resCourseVO?.classAddress?:""

            txt_pin.text = "36人在拼单"
        }


    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.txt_more -> MapActivity.start(requireContext(), lan, lon, name)
        }
    }

}