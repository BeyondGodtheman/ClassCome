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
import com.sunny.classcome.widget.popup.LocationPopup
import kotlinx.android.synthetic.main.fragment_class_list.*

class ClassListFragment : BaseFragment() {
    private var locationFlag = false

    private var sortIndex = 0

    private var sortFlag = false

    val dataList = arrayListOf<ClassBean.Bean.Data>()

    private val topArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_hot_top, img_price_top, img_time_top)
    }

    private val bottomArrowList: ArrayList<ImageView> by lazy {
        arrayListOf(img_hot_bottom, img_price_bottom, img_time_bottom)
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
                locationAction()
            }
            R.id.ll_hot -> {
                sort(0)
            }
            R.id.ll_price -> {
                sort(1)
            }
            R.id.ll_time -> {
                sort(2)
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

        if (sortFlag) {
            topArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_top_blue)
            sortFlag = false
        } else {
            bottomArrowList[sortIndex].setImageResource(R.mipmap.ic_arrow_bottom_blue)
            sortFlag = true
        }
    }


    //地点事件
    private fun locationAction() {
        if (locationFlag) {
            img_location.setImageResource(R.mipmap.ic_arrow_bottom_gray)
        } else {
            img_location.setImageResource(R.mipmap.ic_arrow_top_blue)
            val locationPopup = LocationPopup(requireContext())
            locationPopup.setOnDismissListener {
                locationAction()
            }
            locationPopup.showAsDropDown(ll_location)
        }
        locationFlag = !locationFlag
    }


    fun loadClass() {
        val params = HashMap<String, String>()
        params["cityId"] =  UserManger.getAddress().split(",")[0]
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