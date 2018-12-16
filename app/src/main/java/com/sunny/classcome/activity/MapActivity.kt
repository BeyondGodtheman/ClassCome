package com.sunny.classcome.activity

import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity

/**
 * Desc 查看地图
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/16 15:57
 */
class MapActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_map

    override fun initView() {
        showTitle(titleManager.defaultTitle("查看地图"))
    }

    override fun onClick(v: View?) {
    }
}