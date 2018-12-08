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
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.MsgBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 14:18
 */
class MyMsgActivity : BaseActivity() {

    private val msgList = arrayListOf<MsgBean>()

    private var pageIndex = 1

    private val myMsgAdapter: MyMsgAdapter by lazy {
        MyMsgAdapter(msgList)
    }

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.myMsg)))

        msgList.add(MsgBean("系统提示", "[米斯特教育]邀请您来参加课程竞标，快来看看", "2018/02/02  13:12:33", "初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示", "[米斯特教育]邀请您来参加课程竞标，快来看看", "2018/02/02  13:12:33", "初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示", "[米斯特教育]邀请您来参加课程竞标，快来看看", "2018/02/02  13:12:33", "初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示", "[米斯特教育]邀请您来参加课程竞标，快来看看", "2018/02/02  13:12:33", "初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示", "[米斯特教育]邀请您来参加课程竞标，快来看看", "2018/02/02  13:12:33", "初中英语班课教研员-要求有专业教学资质"))

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


        ApiManager.post(composites, params, Constant.COURSE_GETMESSAGELIST, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {

            }

            override fun onFailed(code: String, message: String) {

            }
        })

    }
}