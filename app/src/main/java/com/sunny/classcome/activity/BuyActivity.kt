package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.BuyAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.BuyBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

class BuyActivity : BaseActivity() {

    private var courseId = ""
    private var pageIndex = 1
    private val list = arrayListOf<BuyBean>()
    private var classBean: ClassBean.Bean.Data? = null

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle("购买者"))
        courseId = intent.getStringExtra("courseId")

        classBean = MyApplication.getApp().getData<ClassBean.Bean.Data>(Constant.COURSE, true)

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
        recl.adapter = BuyAdapter(list,classBean) {
            option(it)
        }
        showLoading()
    }

    override fun onClick(v: View?) {
    }

    override fun loadData() {
        val params = hashMapOf<String, String>()
        params["pageIndex"] = pageIndex.toString()
        params["courseId"] = courseId
        ApiManager.post(composites, params, Constant.ORDER_GETCOURSEUSER_ORDER, object : ApiManager.OnResult<BaseBean<ArrayList<BuyBean>>>() {
            override fun onSuccess(data: BaseBean<ArrayList<BuyBean>>) {
                hideLoading()
                if (pageIndex == 1) {
                    list.clear()
                    refresh.finishRefresh()
                    if (data.content?.data == null || data.content?.data?.isEmpty() == true) {
                        ll_error.visibility = View.VISIBLE
                    } else {
                        ll_error.visibility = View.GONE
                    }
                } else {
                    refresh.finishLoadMore()
                }
                data.content?.data?.let {
                    if (!list.containsAll(it)) {
                        list.addAll(it)
                    }
                }

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

    companion object {
        fun start(context: Context, courseId: String,classBean: ClassBean.Bean.Data) {
            MyApplication.getApp().setData(Constant.COURSE, classBean)
            context.startActivity(Intent(context, BuyActivity::class.java)
                    .putExtra("courseId", courseId))
        }
    }

    private fun option(position: Int) {
        showLoading()
        val params = hashMapOf<String, String>()
        params["courseId"] = courseId
        params["useUserId"] = list[position].userId ?: ""
        ApiManager.post(composites, params, Constant.ORDER_ACCOUNTSORDER, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1") {
                    list[position].state = "3"
                    recl.adapter?.notifyDataSetChanged()
                }

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

    }

    override fun update() {
        recl.adapter?.notifyDataSetChanged()
    }
}