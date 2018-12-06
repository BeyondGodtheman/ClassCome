package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.sunny.classcome.R
import com.sunny.classcome.adapter.LevelDescAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.LevelDesc
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.activity_level_desc.*

class LevelDescActivity: BaseActivity() {
    private val list = arrayListOf<LevelDesc>()

    private val levelDescAdapter: LevelDescAdapter by lazy {
        LevelDescAdapter(list)
    }

    override fun setLayout(): Int = R.layout.activity_level_desc
    override fun initView() {
        showTitle(titleManager.defaultTitle("等级说明"))
        recl.layoutManager = LinearLayoutManager(this)
        recl.adapter = levelDescAdapter
    }

    override fun onClick(v: View) {

    }


    override fun loadData() {
        showLoading()
        ApiManager.post(composites,null,Constant.USER_GETGRADERULE,object :ApiManager.OnResult<BaseBean<ArrayList<LevelDesc>>>(){
            override fun onSuccess(data: BaseBean<ArrayList<LevelDesc>>) {
                hideLoading()
                list.clear()
                data.content?.data?.let {
                    list.addAll(it)
                    levelDescAdapter.notifyDataSetChanged()
                }
                txt_desc.text = data.content?.info?:""
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }
}