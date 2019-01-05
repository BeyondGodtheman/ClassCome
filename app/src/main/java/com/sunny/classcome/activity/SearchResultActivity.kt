package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.android.synthetic.main.activity_class_list.*
import org.json.JSONArray

class SearchResultActivity : BaseActivity() {

    private var sortIndex = -1
    private var pageIndex = 1
    private var sortFlag = false

    private var keyWord = ""
    private var courseType = "2"
    private var category = "0"
    private var startPrice = ""
    private var endPrice = ""
    private var cityId = ""
    private var countyId = ""
    private var townId = ""
    private var startDate = ""
    private var personType = ""

    val dataList = arrayListOf<ClassBean.Bean.Data>()

    private val topArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_top, img_hot_top, img_price_top, img_time_top)
    }

    private val bottomArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_bottom, img_hot_bottom, img_price_bottom, img_time_bottom)
    }

    private val textSortList :ArrayList<TextView> by lazy {
        arrayListOf(txt_sort_location,txt_sort_hot,txt_sort_price,txt_sort_time)
    }

    override fun setLayout(): Int = R.layout.activity_class_list
    override fun initView() {
        tabLayout.visibility = View.GONE

        keyWord = intent.getStringExtra("keyWord")
        courseType = intent.getStringExtra("courseType") ?: ""
        category = intent.getStringExtra("category") ?: ""
        startPrice = intent.getStringExtra("startPrice") ?: ""
        endPrice = intent.getStringExtra("endPrice") ?: ""
        cityId = UserManger.getAddress().split(",")[0]
        countyId = intent.getStringExtra("countyId") ?: ""
        townId = intent.getStringExtra("townId") ?: ""
        startDate = intent.getStringExtra("startDate") ?: ""
        personType = intent.getStringExtra("personType") ?: ""

        showTitle(titleManager.searchTitle(keyWord){
            keyWord = it
            showLoading()
            loadClass()
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

        showLoading()
       loadClass()
    }


    private fun sort(mSortIndex: Int) {
        if (sortIndex != -1){
            bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_gray)
            topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_gray)
            textSortList[sortIndex].setTextColor(ContextCompat.getColor(this,R.color.color_default_font))
            if (mSortIndex != sortIndex) {
                sortFlag = false
            }
        }
        sortIndex = mSortIndex

        textSortList[sortIndex].setTextColor(ContextCompat.getColor(this,R.color.color_nav_blue))
        sortFlag = if (sortFlag) {
            topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_blue)
            false
        } else {
            bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_blue)
            true
        }
        pageIndex = 1
        showLoading()
        loadClass()
    }


    private fun loadClass() {
        val sortStr = when (sortIndex) {
            0 -> "sortAdress"
            1 -> "sortPopularity"
            2 -> "sortPrice"
            3 -> "sortTime"
            else -> ""
        }


        val params = HashMap<String, Any>()
        if (cityId.isNotEmpty()){
            params["cityId"] = cityId
        }

        if (countyId.isNotEmpty()){
            params["countyId"] = countyId
        }
        if (townId.isNotEmpty()){
            params["townId"] = townId
        }

        if (sortStr.isNotEmpty()){
            params[sortStr] = if (!sortFlag) "1" else "0"
        }

        if (category.isNotEmpty()){
            val categoryArray = JSONArray()
            if (courseType == "4" || courseType == "5"){
                categoryArray.put(category)
            }else{
                category.split(",").forEach {
                    if (it.isNotEmpty())
                    categoryArray.put(it)
                }
            }
            params["category"] = categoryArray
        }

        if (keyWord.isNotEmpty()){
            params["keyWord"] = keyWord
        }

        if (personType.isNotEmpty()){
            params["personType"] = personType //人员类型: 1 成人 2 儿童 3所有人
        }

        if (startDate != "设置日期"){
            params["startTime"] = startDate
        }

        if (startPrice.isNotEmpty()){
            params["startPrice"] = startPrice
        }

        if (endPrice.isNotEmpty()){
            params["endPrice"] = endPrice
        }

      if (courseType.isNotEmpty()){
          params["courseType"] = courseType
      }

        params["pageIndex"] = pageIndex.toString()
        ApiManager.post(composites, params, Constant.COURSE_GETCOURSELISTS, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                hideLoading()
                if (pageIndex == 1) {
                    dataList.clear()
                    refresh.finishRefresh()
                    if (data.content?.dataList == null) {
                        layout_error.visibility = View.VISIBLE
                    } else {
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


    companion object {
        fun start(context: Context,keyWord:String ,courseType: String, category: String, startPrice: String, endPrice: String, cityId: String, countyId: String,
                  townId: String, startDate: String, personType: String) {
            context.startActivity(Intent(context, SearchResultActivity::class.java)
                    .putExtra("keyWord", keyWord)
                    .putExtra("courseType", courseType)
                    .putExtra("category", category)
                    .putExtra("startPrice", startPrice)
                    .putExtra("endPrice", endPrice)
                    .putExtra("cityId", cityId)
                    .putExtra("countyId", countyId)
                    .putExtra("townId", townId)
                    .putExtra("startDate", startDate)
                    .putExtra("personType", personType))
        }
    }
}