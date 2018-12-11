package com.sunny.classcome.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.MyParticipatedAdapter
import com.sunny.classcome.adapter.MyPostedAdapter
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.Posted
import kotlinx.android.synthetic.main.layout_refresh_recycler.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MyClassFragment : BaseFragment() {

    val list = arrayListOf<ClassBean.Bean.Data>()
    var pageIndex = 1
    private var type = "1"
    private var status = "7"


    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {

        EventBus.getDefault().register(this)

        refresh.setRefreshHeader(ClassicsHeader(context))
        refresh.setRefreshFooter(ClassicsFooter(context))

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


        recl.layoutManager = LinearLayoutManager(context)
        if (type == "1") {
            recl.adapter = MyPostedAdapter(list)
        } else {
            recl.adapter = MyParticipatedAdapter(list)
        }

    }

    override fun onClick(v: View) {

    }

    override fun loadData() {
        val params = hashMapOf<String, String>()
        params["pageIndex"] = pageIndex.toString()
        params["relationType"] = type
        params["state"] = status

        ApiManager.post(getBaseActivity().composites, params, Constant.ORDER_QUERYMYRELATIONCOURSE, object : ApiManager.OnResult<ClassBean>() {
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

    fun setStatus(type: String, status: String): MyClassFragment {
        this.type = type
        this.status = status

        return this
    }


    //更新列表数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUpdateEvent(posted: Posted){
        loadData()
    }

    
    override fun close() {
        EventBus.getDefault().unregister(this)
    }
}