package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.MyChequesDetailAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ChequesDetailBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ErrorViewType
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc 我的收款明细
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:09
 */
class MyChequesDetailActivity : BaseActivity() {

    private val list = ArrayList<ChequesDetailBean.Bean.Data>()
    private var pageIndex = 1

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.detailed)))


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
        recl.adapter = MyChequesDetailAdapter(list)

    }

    override fun onClick(v: View) {

    }

    override fun loadData() {
        showLoading()
        val params = hashMapOf<String, String>()
        params["pageIndex"] = pageIndex.toString()
        ApiManager.post(composites, params, Constant.ORDER_GETMYSTREAM, object : ApiManager.OnResult<ChequesDetailBean>() {
            override fun onSuccess(data: ChequesDetailBean) {

                hideLoading()

                if (pageIndex == 1) {
                    list.clear()
                    refresh.finishRefresh()
                    if (data.content?.dataList == null) {
                        showError(ErrorViewType("200", getString(R.string.empty_data)))
                    }

                } else {
                    refresh.finishLoadMore()
                }
                list.addAll(data.content?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }
}