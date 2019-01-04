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
import com.sunny.classcome.adapter.ApplicantsAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ApplicantsBean
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.Posted
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_refresh_recycler.*
import org.greenrobot.eventbus.EventBus

class ApplicantsActivity : BaseActivity() {
    private var courseId = ""
    private var pageIndex = 1
    private val list = arrayListOf<ApplicantsBean.Bean.Data>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle("应聘者"))
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
        recl.adapter = ApplicantsAdapter(list, object : ApplicantsAdapter.ApplicantsOption {
            override fun candidate(position: Int) {
                option("2", "1", position)
            }

            override fun cancelCandidate(position: Int) {
                option("1", "1", position)
            }

            override fun winningBid(position: Int) {
                option("", "2", position)
            }
        })

        showLoading()
    }

    override fun onClick(v: View?) {

    }

    override fun loadData() {
        val params = hashMapOf<String, String>()
        params["pageIndex"] = pageIndex.toString()
        params["courseId"] = courseId
        ApiManager.post(composites, params, Constant.ORDER_GETAPPLICANTLIST, object : ApiManager.OnResult<ApplicantsBean>() {
            override fun onSuccess(data: ApplicantsBean) {
                hideLoading()
                if (pageIndex == 1) {
                    list.clear()
                    refresh.finishRefresh()
                    if (data.content?.dataList == null){
                        ll_error.visibility = View.VISIBLE
                    }else{
                        ll_error.visibility = View.GONE
                    }
                } else {
                    refresh.finishLoadMore()
                }
                list.addAll(data.content?.dataList ?: arrayListOf())
                recl.adapter?.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
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


    //操作方法
    fun option(state: String, type: String, position: Int) {
        val params = hashMapOf<String, String>()
        params["courseId"] = courseId
        params["state"] = state
        params["type"] = type
        params["userId"] = list[position].userId?:""
        showLoading()
        ApiManager.post(composites, params, Constant.ORDER_APPLYCOURSE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (type == "1"){
                    if (data.content?.statu == "1") {
                        list[position].state = state
                    }else{
                        list[position].state = type
                    }
                }else{
                    list[position].state = "3"
                }

                recl.adapter?.notifyDataSetChanged()
                EventBus.getDefault().post(Posted())
                ToastUtil.show(data.content?.info)
            }

            override fun onFailed(code: String, message: String) {
            hideLoading()
            }

        })
    }

    companion object {
        fun start(context: Context, courseId: String) {
            context.startActivity(Intent(context, ApplicantsActivity::class.java)
                    .putExtra("courseId", courseId))
        }
    }
}