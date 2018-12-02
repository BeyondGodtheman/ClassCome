package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.activity.*
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
        txt_points.setOnClickListener(this)
        txt_member.text = "黄金会员"
        txt_member.setOnClickListener(this)

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
            R.id.img_more -> intent(MineActivity::class.java)
            R.id.img_message -> intent(MyMsgActivity::class.java)

            R.id.txt_points -> intent(PointActivity::class.java)
            R.id.txt_member -> intent(MineActivity::class.java)

            R.id.rl_my_trip -> intent(MineActivity::class.java)
            R.id.rl_my_publish -> intent(MineActivity::class.java)
            R.id.rl_my_partake -> intent(MineActivity::class.java)

            R.id.txt_my_cheques -> intent(MineActivity::class.java)
            R.id.txt_invitation_record -> intent(MineActivity::class.java)
            R.id.txt_my_profile -> intent(MineActivity::class.java)
            R.id.txt_my_collection -> intent(MyCollectionActivity::class.java)
            R.id.txt_feedback -> intent(FeedbackActivity::class.java)
            R.id.txt_setting -> intent(SettingActivity::class.java)
            R.id.txt_business_cooperation -> intent(MineActivity::class.java)
        }
    }

    private fun intent(clazz: Class<*>) {
        IntentUtil.start(requireActivity(), clazz)
    }
}