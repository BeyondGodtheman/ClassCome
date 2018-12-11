package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.InviteAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.InviteBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

class InviteActivity : BaseActivity() {
    private var courseId = ""
    var pageIndex = 1

    val list = arrayListOf<InviteBean.Bean.Data>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {

        showTitle(titleManager.defaultTitle("邀请"))

        courseId = intent.getStringExtra("courseId")

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
        recl.adapter = InviteAdapter(list) {
            invite(it)
        }
    }

    override fun onClick(v: View?) {
    }

    companion object {
        fun start(context: Context, courseId: String) {
            context.startActivity(Intent(context, InviteActivity::class.java)
                    .putExtra("courseId", courseId))
        }
    }

    //加载邀请列表
    override fun loadData() {
        val params = hashMapOf<String, String>()
        params["pageIndex"] = pageIndex.toString()
        params["courseId"] = courseId
        ApiManager.post(composites, params, Constant.ORDER_MATCHAPPLICANTLIST, object : ApiManager.OnResult<InviteBean>() {
            override fun onSuccess(data: InviteBean) {
                if (pageIndex == 1) {
                    list.clear()
                    refresh.finishRefresh()
                } else {
                    refresh.finishLoadMore()
                }
                list.addAll(data.content?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                if (pageIndex == 1) {
                    list.clear()
                    refresh.finishRefresh()
                    recl.adapter?.notifyDataSetChanged()
                } else {
                    refresh.finishLoadMore()
                }
            }

        })

    }

    //邀请用户
    private fun invite(position:Int){
        val params = hashMapOf<String, String>()
        params["userId"] = list[position].userId
        params["courseId"] = courseId
        ApiManager.post(composites, params, Constant.ORDER_INVITETEACHER, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu =="1"){
                    list[position].isWelcome = "2"
                    recl.adapter?.notifyDataSetChanged()
                }
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }
}