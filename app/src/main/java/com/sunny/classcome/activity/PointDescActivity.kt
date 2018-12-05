package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PointDescAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ScoreRuleBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.activity_point_desc.*

/**
 * Desc 积分说明
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:58
 */
class PointDescActivity : BaseActivity() {

    private val noviceList = ArrayList<ScoreRuleBean.Bean.Data>()

    private val dailyList = ArrayList<ScoreRuleBean.Bean.Data>()

    private val noviceAdapter:PointDescAdapter by lazy {
        PointDescAdapter(noviceList)
    }

    private val dailyAdapter:PointDescAdapter by lazy {
        PointDescAdapter(dailyList)
    }

    override fun setLayout(): Int = R.layout.activity_point_desc

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.point_description)))


        recl_novice.layoutManager = LinearLayoutManager(this)
        recl_novice.adapter = noviceAdapter


        recl_daily.layoutManager = LinearLayoutManager(this)
        recl_daily.adapter = dailyAdapter
    }

    override fun onClick(v: View?) {
    }


    override fun loadData() {
        showLoading()
        ApiManager.post(composites,null,Constant.USER_GETSCORERULE,object :ApiManager.OnResult<ScoreRuleBean>(){
            override fun onSuccess(data: ScoreRuleBean) {
                hideLoading()
                noviceList.clear()
                dailyList.clear()

                data.content?.novice?.let {
                    noviceList.addAll(it)
                }

                data.content?.daily?.let {
                    dailyList.addAll(it)
                }

                noviceAdapter.notifyDataSetChanged()

                dailyAdapter.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }
}