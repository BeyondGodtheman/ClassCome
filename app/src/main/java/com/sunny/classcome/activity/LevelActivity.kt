package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PointAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.PointBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_point.*

class LevelActivity : BaseActivity() {
    private val list = ArrayList<PointBean.Bean.Data>()

    private val noviceAdapter: PointAdapter by lazy {
        PointAdapter(list)
    }

    override fun setLayout(): Int = R.layout.activity_point

    override fun initView() {
        goneTitle()

        txt_point.text = intent.getStringExtra("level") ?: getString(R.string.ordinary_member)

        txt_title.text = "等级"
        txt_desc.text = "我的等级"

        ll_back.setOnClickListener(this)
        ll_prompt.setOnClickListener(this)


        recl_point.layoutManager = LinearLayoutManager(this)
        recl_point.adapter = noviceAdapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_back -> finish()
            R.id.ll_prompt -> IntentUtil.start(this, LevelDescActivity::class.java)
        }
    }

    override fun loadData() {
        showLoading()
        ApiManager.post(composites, null, Constant.USER_GETMYGRADE, object : ApiManager.OnResult<BaseBean<PointBean.Bean>>() {
            override fun onSuccess(data: BaseBean<PointBean.Bean>) {
                hideLoading()
                list.clear()
                data.content?.data?.dataList?.let {
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