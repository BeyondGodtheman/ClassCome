package com.sunny.classcome.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.sunny.classcome.R
import com.sunny.classcome.adapter.ClassListAdapter
import com.sunny.classcome.base.BaseFragment
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.fragment_class_list.*

class ClassListFragment : BaseFragment() {

    private var sortIndex = 0

    private var sortFlag = false

    val dataList = arrayListOf<ClassBean.Bean.Data>()

    private val topArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_top, img_hot_top, img_price_top, img_time_top)
    }

    private val bottomArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_local_bottom, img_hot_bottom, img_price_bottom, img_time_bottom)
    }


    override fun setLayout(): Int = R.layout.fragment_class_list

    override fun initView() {
        ll_location.setOnClickListener(this)
        ll_hot.setOnClickListener(this)
        ll_price.setOnClickListener(this)
        ll_time.setOnClickListener(this)


        recl.layoutManager = LinearLayoutManager(context)
        recl.isNestedScrollingEnabled = false
        recl.adapter = ClassListAdapter(dataList)
        sort(0)
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
        bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_gray)
        topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_gray)
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

        loadClass()
    }


    fun loadClass() {
        val sortStr = when (sortIndex) {
            0 -> "sortAdress"
            1 -> "sortPopularity"
            2 -> "sortPrice"
            else -> "sortTime"
        }
        val params = HashMap<String, String>()
        params["cityId"] = UserManger.getAddress().split(",")[0]
        params[sortStr] = if (sortFlag) "1" else "0"

        ApiManager.post(getBaseActivity().composites, params, Constant.COURSE_GETCOURSELISTS, object : ApiManager.OnResult<ClassBean>() {
            override fun onSuccess(data: ClassBean) {
                dataList.clear()
                data.content.dataList?.let {
                    dataList.addAll(it)
                    recl.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailed(code: String, message: String) {
            }

        })
    }
}