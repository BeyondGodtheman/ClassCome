package com.sunny.classcome.activity

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.MyMsgAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.MsgBean
import kotlinx.android.synthetic.main.layout_refresh_recycler.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 14:18
 */
class MyMsgActivity : BaseActivity() {

    private val msgList = arrayListOf<MsgBean>()

    private val myMsgAdapter: MyMsgAdapter by lazy {
        MyMsgAdapter(msgList)
    }

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.myMsg)))

        msgList.add(MsgBean("系统提示","[米斯特教育]邀请您来参加课程竞标，快来看看","2018/02/02  13:12:33","初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示","[米斯特教育]邀请您来参加课程竞标，快来看看","2018/02/02  13:12:33","初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示","[米斯特教育]邀请您来参加课程竞标，快来看看","2018/02/02  13:12:33","初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示","[米斯特教育]邀请您来参加课程竞标，快来看看","2018/02/02  13:12:33","初中英语班课教研员-要求有专业教学资质"))
        msgList.add(MsgBean("系统提示","[米斯特教育]邀请您来参加课程竞标，快来看看","2018/02/02  13:12:33","初中英语班课教研员-要求有专业教学资质"))

        recl.layoutManager = LinearLayoutManager(this)
        recl.addItemDecoration(object :RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                if (parent.indexOfChild(view) == 0){
                    outRect.top = resources.getDimension(R.dimen.dp26).toInt()
                }
                outRect.bottom = resources.getDimension(R.dimen.dp30).toInt()
            }
        })

        recl.adapter = myMsgAdapter

        krl.setKRefreshListener {

            launch(UI){
                delay(3000)
                krl.refreshComplete(false)
            }

        }
    }

    override fun onClick(v: View?) {
    }
}