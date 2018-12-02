package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.FeedbackActivity
import com.sunny.classcome.activity.MineActivity
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * Desc：我的
 * Author：JoannChen
 * Mail：yongzuo_chen@dingyuegroup.cn
 * Date：2018/11/22 0022 18:29
 */
class MineFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.fragment_mine

    override fun initView() {

        img_user_head.setImageResource(R.mipmap.ic_default_head)
        txt_user_name.text = "课多多"
        txt_user_address.text = "上海"
        txt_points.text = "2000积分"
        txt_member.text = "黄金会员"

        img_message.setOnClickListener(this)
        img_user_head.setOnClickListener(this)
        img_more.setOnClickListener(this)

        rl_my_trip.setOnClickListener(this)
        rl_my_publish.setOnClickListener(this)
        rl_my_partake.setOnClickListener(this)

        txt_my_cheques.setOnClickListener(this)
        txt_invitation_record.setOnClickListener(this)
        txt_my_profile.setOnClickListener(this)
        txt_my_collection.setOnClickListener(this)
        txt_feedback.setOnClickListener(this)
        txt_setting.setOnClickListener(this)
        txt_business_cooperation.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_user_head,
            R.id.img_more -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.img_message -> IntentUtil.start(requireActivity(), MineActivity::class.java)

            R.id.txt_points -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.txt_member -> IntentUtil.start(requireActivity(), MineActivity::class.java)

            R.id.rl_my_trip -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.rl_my_publish -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.rl_my_partake -> IntentUtil.start(requireActivity(), MineActivity::class.java)

            R.id.txt_my_cheques -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.txt_invitation_record -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.txt_my_profile -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.txt_my_collection -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.txt_feedback -> IntentUtil.start(requireActivity(), FeedbackActivity::class.java)
            R.id.txt_setting -> IntentUtil.start(requireActivity(), MineActivity::class.java)
            R.id.txt_business_cooperation -> IntentUtil.start(requireActivity(), MineActivity::class.java)
        }
    }
}