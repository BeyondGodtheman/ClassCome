package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.activity.MapActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_course_details.*

/**
 * Desc 课程详情
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/15 20:40
 */
class CourseDetailsFragment : BaseFragment() {

    private var lan = "0"
    private var lon = "0"
    private var name = ""

    override fun setLayout(): Int = R.layout.fragment_course_details

    override fun initView() {

        MyApplication.getApp().getData<ClassDetailBean>(Constant.CLASS_DETAIL, true)?.content?.resCourseVO?.let { bean ->

            lan = bean.course.latitude

            lon = bean.course.longitude

            name = bean.classAddress

            // 课程总节数/招聘人数
            txt_class_total.text = ("${bean.courseNum}节")
            txt_recruit.text = ("接口未定义字段")

            // 单节酬劳/酬劳总价
            txt_price.text = ("￥${bean.price}")
            txt_sum.text = ("￥${bean.sumPrice}")

            // 课程类别
            if (bean.category?.isNotEmpty() == true) {
                txt_class_type.text = bean.category!![0]
            }

            // 人员类型/课程日期
            txt_person_type.text = bean.personType
            txt_class_date.text = ("${DateUtil.dateFormatYYMMdd(bean.startTime
                    ?: "")}至${DateUtil.dateFormatYYMMdd(bean.endTime ?: "")}")

            // 上课时段
            if (bean.classTime?.isNotEmpty() == true) {
                txt_time.text = bean.classTime!![0]
            }

            // 截至日期/上课地点
            txt_by_date.text = DateUtil.dateFormatYYMMdd(bean.course.expirationTime)
            txt_address.text = bean.classAddress
        }


        ll_map.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_map -> MapActivity.start(requireContext(), lan, lon, name)
        }
    }


}