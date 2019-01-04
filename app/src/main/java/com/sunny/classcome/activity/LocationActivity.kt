package com.sunny.classcome.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.LocationCityAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.LocalCityBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_location.*


/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 17:45
 */
class LocationActivity : BaseActivity() {
    var type = 0

    private val cityList = arrayListOf<LocalCityBean.City>()

    private val locationCityAdapter by lazy {
        LocationCityAdapter(cityList).apply {
            setOnItemClickListener { _, index ->
                if (type == 0){
                    UserManger.setAddress(cityList[index].cityVoId, cityList[index].cityVoName)
                    setResult(type)

                }else{
                    setResult(type,Intent().putExtra("cityName",cityList[index].cityVoName))
                }
                finishAfterTransition()
            }
        }
    }

    override fun setLayout(): Int = R.layout.activity_location

    override fun initView() {
        showTitle(titleManager.defaultTitle(""))

        type = intent.getIntExtra("type",0)

        MyApplication.getApp().getData<String>(Constant.LOCATION_NAME, false).let {
            if (it.isNullOrEmpty()){
                txt_location_name.text = getString(R.string.defaultLocation)
            }else{
                txt_location_name.text = it
            }

        }
        recl_city.layoutManager = LinearLayoutManager(this)
        recl_city.adapter = locationCityAdapter
        initData()
    }


    private fun initData() {
        showLoading()
        ApiManager.post(composites, null, Constant.PUB_GETCITYLIST, object : ApiManager.OnResult<LocalCityBean>() {
            override fun onSuccess(data: LocalCityBean) {
                hideLoading()
                cityList.clear()
                cityList.addAll(data.content)
                locationCityAdapter.notifyDataSetChanged()
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    override fun onClick(v: View) {
    }
}