package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.LocationCityAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.LocalCityBean
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.activity_location.*
import java.util.*


/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 17:45
 */
class LocationActivity : BaseActivity() {


   private val cityList = arrayListOf<LocalCityBean>()

    private val indexMap = HashMap<String, Int>()

    private val locationCityAdapter by lazy {
        LocationCityAdapter(cityList)
    }

    override fun setLayout(): Int = R.layout.activity_location

    override fun initView() {
        showTitle(titleManager.defaultTitle(""))
        MyApplication.getApp().getData<String>(Constant.LOCATION_NAME, false).let {
            txt_location_name.text = it ?: getString(R.string.defaultLocation)
        }

        recl_city.layoutManager  = LinearLayoutManager(this)
        initData()
    }



    private fun initData() {

    }

    override fun onClick(v: View?) {
    }
}