package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.ClassListAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc 我的收藏
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 22:21
 */
class MyCollectionActivity : BaseActivity() {

    private var pageIndex = 1

    private val list = arrayListOf<ClassBean.Bean.Data>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.my_collection)))

        refresh.setRefreshHeader(ClassicsHeader(this))
        refresh.setRefreshFooter(ClassicsFooter(this))

        refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageIndex = 1
                loadData()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pageIndex++
                loadData()
            }
        })

        recl.layoutManager = LinearLayoutManager(this)
        recl.adapter = ClassListAdapter(list)

    }

    override fun onClick(v: View?) {

    }

    override fun loadData() {
        val params = hashMapOf<String, String>()
        params["pageIndex"] = pageIndex.toString()
        ApiManager.post(composites, params, Constant.COURSE_GETMYFAVORITELIST, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                if (pageIndex == 1) {
                    refresh.finishRefresh()
                    list.clear()
                    if (data.content?.dataList == null) {
                        ll_error.visibility = View.VISIBLE
                    } else {
                        ll_error.visibility = View.GONE
                    }
                } else {
                    refresh.finishLoadMore()
                }
                list.addAll(data.content?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                if (pageIndex == 1) {
                    refresh.finishRefresh()
                } else {
                    refresh.finishLoadMore()
                }
            }
        })
    }
}