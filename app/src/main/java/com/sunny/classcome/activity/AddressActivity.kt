package com.sunny.classcome.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.AddressCountyAdapter
import com.sunny.classcome.adapter.AddressTownAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.GeocodeBean
import com.sunny.classcome.bean.LocalCityBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_address.*

class AddressActivity: BaseActivity() {

    private var countyName = ""

    override fun setLayout(): Int = R.layout.activity_address

    override fun initView() {
        showTitle(titleManager.defaultTitle("切换位置"))
        recl_city.layoutManager = LinearLayoutManager(this)
        recl_area.layoutManager = LinearLayoutManager(this)

        if (intent.getBooleanExtra("search",false)){
            txt_unlimited.visibility = View.VISIBLE

            txt_unlimited.setOnClickListener {
                setResult(1, Intent())
                finishAfterTransition()
            }
        }
    }

    override fun onClick(v: View?) {

    }


    val resultIntent = Intent()
    override fun loadData() {
        showLoading()
        val address = UserManger.getAddress().split(",")
        txt_city.text = address[1]

        ApiManager.post(composites, null, Constant.PUB_GETCITYLIST, object : ApiManager.OnResult<LocalCityBean>() {
            override fun onSuccess(data: LocalCityBean) {
                hideLoading()

                resultIntent.putExtra("cityId",address[0])

                recl_city.adapter = AddressCountyAdapter(data.content.find { it.cityVoId == address[0]}?.countyList?: arrayListOf()).apply {
                    setOnItemClickListener { _, index ->
                        lastIndex = index
                        countyName = getData(index).countyName
                        resultIntent.putExtra("countyId",getData(index).countyId)
                        resultIntent.putExtra("countyName",countyName)

                        notifyDataSetChanged()
                        recl_area.adapter = AddressTownAdapter(getData(index).townlist).apply {
                            setOnItemClickListener { _, index ->
                                getLatLon(address[1],countyName + getData(index).townName,getData(index).townId,getData(index).townName)
                            }
                        }
                    }
                }
            }


            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }


    fun getLatLon(city:String,address:String,townId:String,townName:String){
        val params = hashMapOf<String,String>()
        params["key"] = "8325164e247e15eea68b59e89200988b"
        params["city"] = city
        params["address"] = address
        ApiManager.get(composites,params,"https://restapi.amap.com/v3/geocode/geo",object :ApiManager.OnResult<GeocodeBean>(){
            override fun onSuccess(data: GeocodeBean) {
                 val location =  data.geocodes[0].location.split(",")
                resultIntent.putExtra("townId",townId)
                resultIntent.putExtra("townName",townName)
                resultIntent.putExtra("latitude",location[1])
                resultIntent.putExtra("longitude",location[0])
                setResult(1, resultIntent)
                finishAfterTransition()
            }

            override fun onFailed(code: String, message: String) {

            }

        })
    }
}