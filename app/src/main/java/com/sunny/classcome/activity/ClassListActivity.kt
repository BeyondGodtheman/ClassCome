package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.classcome.R
import com.sunny.classcome.adapter.ClassListAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.ClassChildType
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_class_list.*
import org.json.JSONArray


class ClassListActivity : BaseActivity() {
    private var sortIndex = -1
    private var pageIndex = 1
    private var sortFlag = false

    //pId 活动 213, 217家教,40000 场地, 50000 培训

    private var pId = "213"
    private var courseType = "2"
    private var category = ""

    val dataList = arrayListOf<ClassBean.Bean.Data>()

    private val topArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_top, img_hot_top, img_price_top, img_time_top)
    }

    private val bottomArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_bottom, img_hot_bottom, img_price_bottom, img_time_bottom)
    }


    override fun setLayout(): Int = R.layout.activity_class_list

    override fun initView() {

        pId = intent.getStringExtra("pId")

        val title = when (pId) {
            "213" -> {
                courseType = "3"
                "活动"
            }
            "217" -> {
                courseType = "1"
                "家教"
            }
            "40000" -> {
                courseType = "4"
                "场地"
            }
            "50000" -> {
                courseType = "5"
                "培训"
            }
            else -> {
                courseType = "2"
                "代课"
            }
        }

        showTitle(titleManager.iconTitle(title, View.OnClickListener {
            SearchActivity.start(this,pId,courseType)
        }))

        tabLayout.visibility = View.VISIBLE

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                category = tab.tag as String
                refresh.closeHeaderOrFooter()
                sort(0)
            }

        })

        ll_location.setOnClickListener(this)
        ll_hot.setOnClickListener(this)
        ll_price.setOnClickListener(this)
        ll_time.setOnClickListener(this)


        recl.layoutManager = LinearLayoutManager(this)
        recl.isNestedScrollingEnabled = false
        recl.adapter = ClassListAdapter(dataList)

        refresh.setRefreshHeader(ClassicsHeader(this))
        refresh.setRefreshFooter(ClassicsFooter(this))

        refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageIndex = 1
                loadClass()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pageIndex++
                loadClass()
            }
        })

        loadNav()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_location -> {
                sort(0)
            }
            R.id.ll_hot -> {
                sort(1)
            }
            R.id.ll_price -> {
                sort(2)
            }
            R.id.ll_time -> {
                sort(3)
            }
        }
    }

    private fun sort(mSortIndex: Int) {

        pageIndex = 1
        bottomArrowList[mSortIndex].setImageResource(R.mipmap.ic_arrow_bottom_gray)
        topArrowList[mSortIndex].setImageResource(R.mipmap.ic_arrow_top_gray)
        if (mSortIndex != sortIndex) {
            sortFlag = false
        }
        sortIndex = mSortIndex

        sortFlag = if (sortFlag) {
            topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_blue)
            false
        } else {
            bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_blue)
            true
        }

        showLoading()
        loadClass()
    }


    private fun loadNav() {
        showLoading()
        val params = HashMap<String, String>()
        if (courseType == "4" || courseType == "5"){
            params["pId"] = pId
        }
        ApiManager.post(composites, params, Constant.COURSE_GETCATEGORY, object : ApiManager.OnResult<ClassChildType>() {
            override fun onSuccess(data: ClassChildType) {
                hideLoading()
                tabLayout.removeAllTabs()
                data.content?.forEach {
                    tabLayout.addTab(tabLayout.newTab().setText(it.name).setTag(it.id),false)
                }
//                data.content?.let {
//                    if (it.isNotEmpty()){
//                        category = it.first().id
//                    }
//                }


                //加载课程数据
                showLoading()
                loadClass()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })


    }


    private fun loadClass() {
        val sortStr = when (sortIndex) {
            0 -> "sortAdress"
            1 -> "sortPopularity"
            2 -> "sortPrice"
            4 -> "sortTime"
            else -> ""
        }


        val params = HashMap<String, Any>()
        params["cityId"] = UserManger.getAddress().split(",")[0]
        if (sortStr.isNotEmpty()){
            params[sortStr] = if (!sortFlag) "1" else "0"
        }

        if (category.isNotEmpty()){
            val categoryArray = JSONArray()
            categoryArray.put(category)
            params["category"] = categoryArray
        }

        params["courseType"] = courseType
        params["pageIndex"] = pageIndex.toString()
        ApiManager.post(composites, params, Constant.COURSE_GETCOURSELISTS, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                hideLoading()
                if (pageIndex == 1) {
                    dataList.clear()
                    refresh.finishRefresh()
                    if (data.content?.dataList == null){
                        layout_error.visibility = View.VISIBLE
                    }else{
                        layout_error.visibility = View.GONE
                    }
                } else {
                    refresh.finishLoadMore()
                }

                data.content?.dataList?.let {
                    dataList.addAll(it)
                    recl.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    companion object {
        fun start(context: Context, pId: String) {
            context.startActivity(Intent(context, ClassListActivity::class.java)
                    .putExtra("pId", pId))
        }
    }
}