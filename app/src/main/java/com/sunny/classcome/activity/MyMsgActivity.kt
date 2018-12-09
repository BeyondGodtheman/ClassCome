package com.sunny.classcome.activity

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.MyMsgAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.MsgBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ErrorViewType
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 14:18
 */
class MyMsgActivity : BaseActivity() {

    private val list = arrayListOf<MsgBean.Content.Bean.Data>()

    private var pageIndex = 1

    private val myMsgAdapter: MyMsgAdapter by lazy {
        MyMsgAdapter(list)
    }

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.myMsg)))

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
        recl.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                if (parent.indexOfChild(view) == 0) {
                    outRect.top = resources.getDimension(R.dimen.pt26).toInt()
                }
                outRect.bottom = resources.getDimension(R.dimen.pt30).toInt()
            }
        })

        recl.adapter = myMsgAdapter

    }

    override fun onClick(v: View) {
    }

    override fun loadData() {
        val params = HashMap<String, String>()
        params["pageIndex"] = pageIndex.toString()
        ApiManager.post(composites, params, Constant.COURSE_GETMESSAGELIST, object : ApiManager.OnResult<MsgBean>() {
            override fun onSuccess(data: MsgBean) {
                if (pageIndex == 1) {
                    list.clear()
                    refresh.finishRefresh()
                    if (data.content?.data?.dataList == null) {
                        showError(ErrorViewType("200", getString(R.string.empty_data)))
                    }

                } else {
                    refresh.finishLoadMore()
                }
                list.addAll(data.content?.data?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()

            }

            override fun onFailed(code: String, message: String) {
                if (pageIndex == 1) {
                    refresh.finishRefresh()
                }else{
                    refresh.finishLoadMore()
                }
            }
        })

    }
}