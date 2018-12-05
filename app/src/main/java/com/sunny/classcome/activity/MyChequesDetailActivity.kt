package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.MyChequesDetailAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ChequesDetailBean
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc 我的收款明细
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:09
 */
class MyChequesDetailActivity : BaseActivity() {

    private val list: ArrayList<ChequesDetailBean> by lazy {
        arrayListOf(
                ChequesDetailBean("支付代课费", "2018-12-05 12:12", "余额：1000.00", "-200"),
                ChequesDetailBean("收到代课费", "2018-12-05 12:12", "余额：5200.00", "-200"),
                ChequesDetailBean("支付代课费", "2018-12-05 11:11", "余额：1314.00", "-200")
        )
    }

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.detailed)))

        krl.setBackgroundResource(R.color.color_white)

        recl.layoutManager = LinearLayoutManager(this)
        recl.adapter = MyChequesDetailAdapter(list)

    }

    override fun onClick(v: View) {

    }
}