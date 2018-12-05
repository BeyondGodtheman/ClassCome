package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PointAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.PointBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_point.*

/**
 * Desc 我的积分
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:58
 */
class PointActivity : BaseActivity() {

    private val list =  ArrayList<PointBean.Bean.Data>()

    private val noviceAdapter:PointAdapter by lazy {
        PointAdapter(list)
    }

    override fun setLayout(): Int = R.layout.activity_point

    override fun initView() {
        goneTitle()

        txt_point.text = intent.getStringExtra("point")?:"0"

        ll_back.setOnClickListener(this)
        ll_prompt.setOnClickListener(this)


        recl_point.layoutManager = LinearLayoutManager(this)
        recl_point.adapter = noviceAdapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_back -> finish()
            R.id.ll_prompt -> IntentUtil.start(this, PointDescActivity::class.java)
        }
    }

    override fun loadData() {
        showLoading()
        ApiManager.post(composites,null,Constant.USER_GETMYSCORE,object :ApiManager.OnResult<PointBean>(){
            override fun onSuccess(data: PointBean) {
                hideLoading()
                list.clear()
                data.content?.dataList?.let {
                    list.addAll(it)
                }
                noviceAdapter.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }
}