package com.sunny.classcome.fragment

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseFragment
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
        txt_user_address.text = "上海"
    }

    override fun update() {
    }



    override fun onClick(v: View) {
    }
}