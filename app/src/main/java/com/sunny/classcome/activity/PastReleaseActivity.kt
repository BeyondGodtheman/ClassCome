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
import com.sunny.classcome.adapter.ClassListAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * 过往发布信息
 */
class PastReleaseActivity: BaseActivity() {

    private var pageIndex = 1

    private var uid = ""

    private val list = arrayListOf<ClassBean.Bean.Data>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle("过往发布信息"))

        uid = intent.getStringExtra("uid")?:""

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
        params["userId"] = uid
        params["pageIndex"] = pageIndex.toString()
        ApiManager.post(composites, params, Constant.CURSE_GETUSERPUBLISHCOURSE, object : ApiManager.OnResult<ClassBean>() {
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

                data.content?.dataList?.filter { it.course.userId == uid}?.let {
                    if (!list.containsAll(it)){
                        list.addAll(it)
                    }
                }

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

    companion object {
        fun start(context: Context,uid:String){
            context.startActivity(Intent(context,PastReleaseActivity::class.java)
                    .putExtra("uid",uid))
        }
    }
}