package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.ItineraryDateAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ItineraryDateBean
import com.sunny.classcome.bean.JourneyBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.activity_itinerary.*
import java.util.*
import kotlin.collections.HashMap

class MyItineraryActivity : BaseActivity() {

    val dataList = arrayListOf<ItineraryDateBean>()

    private val afterCalendar: Calendar by lazy {
        Calendar.getInstance()
    }

    private val beforeCalendar: Calendar by lazy {
        Calendar.getInstance()
    }

    private  var selectDate = ""

    override fun setLayout(): Int = R.layout.activity_itinerary

    override fun initView() {
        showTitle(titleManager.defaultTitle("我的行程"))

        recl_date.layoutManager = LinearLayoutManager(this)
        recl_date.adapter = ItineraryDateAdapter(dataList).apply {
            setOnItemClickListener { _, index ->
                select(index)
                selectDate = dataList[index].date
                loadMyJourney()
            }
        }

        refresh.setRefreshHeader(BezierRadarHeader(this).setPrimaryColorId(R.color.color_white)
                .setAccentColorId(R.color.color_nav_blue))
        refresh.setRefreshFooter(ClassicsFooter(this))
        refresh.setOnRefreshLoadMoreListener(object :OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                loadData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                loadBeforeData()
            }
        })

        refresh2.setRefreshHeader(ClassicsHeader(this))
        refresh2.setOnRefreshListener {
            loadMyJourney()
        }

        beforeCalendar.add(Calendar.DAY_OF_MONTH, -1)

    }

    override fun onClick(v: View) {

    }

    override fun loadData() {

        for (i in 0 until 30) {
            val year = afterCalendar.get(Calendar.YEAR)
            val month = afterCalendar.get(Calendar.MONTH) + 1
            val day = afterCalendar.get(Calendar.DAY_OF_MONTH)
            val week = afterCalendar.get(Calendar.DAY_OF_WEEK)
            dataList.add(ItineraryDateBean("${day}日", ("$year-$month-$day"), week -1))
            afterCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        refresh.finishLoadMore()
        recl_date.adapter?.notifyDataSetChanged()
    }

    fun loadBeforeData() {
        val resultList = arrayListOf<ItineraryDateBean>()
        for (i in 0 until 30) {
            val year = beforeCalendar.get(Calendar.YEAR)
            val month = beforeCalendar.get(Calendar.MONTH) + 1
            val day = beforeCalendar.get(Calendar.DAY_OF_MONTH)
            val week = beforeCalendar.get(Calendar.DAY_OF_WEEK)
            beforeCalendar.add(Calendar.DAY_OF_MONTH, -1)
            resultList.add(ItineraryDateBean("${day}日", ("$year-$month-$day"), week -1))
        }
        resultList.reverse()
        dataList.addAll(0,resultList)

        refresh.finishRefresh()
        recl_date.adapter?.notifyDataSetChanged()
        recl_date.scrollToPosition(37)
    }

    private fun loadMyJourney(){
        val params = HashMap<String,String>()
        params["startTime"] = selectDate
        ApiManager.post(composites,params,Constant.COURSE_GETMYJOURNEY,object :ApiManager.OnResult<JourneyBean>(){
            override fun onSuccess(data: JourneyBean) {
                refresh2.finishRefresh()
            }

            override fun onFailed(code: String, message: String) {

            }

        })

    }
}