package com.sunny.classcome.fragment

import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.MapActivity
import com.sunny.classcome.activity.MyProfileActivity
import com.sunny.classcome.activity.PastReleaseActivity
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.DateUtil
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.fragment_course_details.*

/**
 * Desc 课程详情
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/15 20:40
 */
class CourseDetailsFragment : BaseFragment() {

    var id = "175073"
    var uid = ""
    var courseId = ""

    override fun setLayout(): Int = R.layout.fragment_course_details

    override fun initView() {
//        id = intent.getStringExtra("id")

        ll_map.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_map -> IntentUtil.start(requireActivity(), MapActivity::class.java)
            R.id.rl_user_more -> startActivity(Intent(requireActivity(), MyProfileActivity::class.java).putExtra("uid", uid))
            R.id.rl_history_more -> IntentUtil.start(requireActivity(), PastReleaseActivity::class.java)
        }
    }


    override fun update() {
        showLoading()
        val map = HashMap<String, String>()
        map["id"] = id
//        map["pintuan"] = id
        ApiManager.post(getBaseActivity().composites, map, Constant.COURSE_GETCOURSEDETAIL, object : ApiManager.OnResult<ClassDetailBean>() {
            override fun onSuccess(data: ClassDetailBean) {
                hideLoading()
                initData(data.content)

                uid = data.content.user.id
                courseId = data.content.resCourseVO.course.id

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

    }


    private fun initData(bean: ClassDetailBean.Content) {

        // 课程总节数/招聘人数
        txt_class_total.text = ("${bean.resCourseVO.courseNum}节")
        txt_recruit.text = ("接口未定义字段")

        // 单节酬劳/酬劳总价
        txt_price.text = ("￥${bean.resCourseVO.price}")
        txt_sum.text = ("￥${bean.resCourseVO.sumPrice}")

        // 课程类别/人员类型/课程日期
        txt_class_type.text = bean.resCourseVO.category[0]
        txt_person_type.text = bean.resCourseVO.personType
        txt_class_date.text = ("${DateUtil.dateFormatYYMMdd(bean.resCourseVO.startTime)}至${DateUtil.dateFormatYYMMdd(bean.resCourseVO.endTime)}")

        // 上课时段/截至日期/上课地点
        txt_time.text = bean.resCourseVO.classTime[0]
        txt_by_date.text = DateUtil.dateFormatYYMMdd(bean.resCourseVO.course.expirationTime)
        txt_address.text = bean.resCourseVO.classAddress

    }
}