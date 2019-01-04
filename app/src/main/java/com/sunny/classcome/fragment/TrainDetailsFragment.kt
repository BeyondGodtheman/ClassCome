package com.sunny.classcome.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.MapActivity
import com.sunny.classcome.adapter.PinTuanAdapter
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassDetailBean
import com.sunny.classcome.widget.dialog.PinTuanDialog
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

        txt_long_desc.text = "培训时长"
        txt_time_desc.text = "培训时间"

        txt_brief_desc.text = "培训简介"

        txt_pintuan_more.setOnClickListener(this)
        img_pintuan_more.setOnClickListener(this)

        classDetailBean.let { bean ->

            lan = bean?.content?.resCourseVO?.latitude ?: "0"

            lon = bean?.content?.resCourseVO?.longitude ?: "0"

            name = bean?.content?.resCourseVO?.classAddress ?: ""

            var number = 0

            bean?.content?.resCourseVO?.pintuanlist?.let { it ->
                number += it.size
                it.forEach {
                    number += (it.tuanyuan ?: arrayListOf()).size
                }
                val list = arrayListOf<ClassDetailBean.Content.ResCourseVO.PintuanResponseVO>()
                list.addAll(it.take(2))
                if (list.isNotEmpty()) {
                    recl_pin.layoutManager = LinearLayoutManager(context)

                    recl_pin.adapter = PinTuanAdapter(bean.content.resCourseVO.isAppointment, ClassBean.Bean.Data(
                            bean.content.resCourseVO.course,
                            bean.content.resCourseVO.materialList,
                            arrayListOf(),
                            ClassBean.Bean.Data.User(bean.content.user.userName, bean.content.user.telephone, bean.content.user.userPic),
                            ClassBean.Bean.Data.Order("", "", "", ""
                                    , bean.content.resCourseVO.price, "", "", "", "", "", if (bean.content.resCourseVO.isAppointment =="5") 1 else 0,null,""),
                            null,bean.content.resCourseVO.isAppraise), list)
                }
            }

            if (number > 0){
                layout_pin.visibility = View.VISIBLE
                txt_pin.text = ("${number}人在拼团")
            }

            txt_brief.text = bean?.content?.resCourseVO?.description?:""
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.txt_more -> MapActivity.start(requireContext(), lan, lon, name)
            R.id.txt_pintuan_more, R.id.img_pintuan_more -> {
                classDetailBean?.let {
                    PinTuanDialog(requireContext(), it.content.resCourseVO.isAppointment, ClassBean.Bean.Data(
                            it.content.resCourseVO.course,
                            it.content.resCourseVO.materialList,
                            arrayListOf(),
                            ClassBean.Bean.Data.User(it.content.user.userName, it.content.user.telephone, it.content.user.userPic),
                            ClassBean.Bean.Data.Order("", "", "", ""
                                    , it.content.resCourseVO.price, "", "", "", "", "", if (it.content.resCourseVO.isAppointment =="5") 1 else 0,null,""),
                            null,it.content.resCourseVO.isAppraise), it.content.resCourseVO.pintuanlist?: arrayListOf()).show()
                }

            }
        }
    }

}