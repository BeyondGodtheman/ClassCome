package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.MapActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.fragment_field_details.*

/**
 * Desc 场地详情
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/15 20:40
 */
class FieldDetailsFragment : BaseFragment() {

    val data: ClassDetailBean by lazy {
        arguments?.getSerializable("data") as ClassDetailBean
    }

    override fun setLayout(): Int = R.layout.fragment_field_details

    override fun initView() {

        val bean = data.content.resCourseVO.course

        // 使用时长/容纳人数
        txt_use_long.text = bean.onetime
        txt_people_count.text = bean.captynum

        // 场地空间/营业时间/场地位置
        txt_square.text = bean.workspace
        txt_time.text = bean.worktime
        txt_address.text = bean.classDetailAdress

        ll_map.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_map -> IntentUtil.start(requireActivity(), MapActivity::class.java)
        }
    }
}