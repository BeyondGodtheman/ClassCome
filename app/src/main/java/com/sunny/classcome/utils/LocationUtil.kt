package com.sunny.classcome.utils

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.sunny.classcome.MyApplication
import com.sunny.classcome.http.Constant

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 17:05
 */
class LocationUtil(context: Context, var onLocation: (String) -> Unit) : AMapLocationListener {

    private val mLocationClient: AMapLocationClient by lazy {
        AMapLocationClient(context).apply {
            setLocationListener(this@LocationUtil)
            setLocationOption(AMapLocationClientOption().apply {
                locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving //低功耗模式
            })
        }
    }

    fun startLocation() {
        mLocationClient.startLocation()
    }

    fun stopLocation() {
        mLocationClient.stopLocation()
    }

    override fun onLocationChanged(location: AMapLocation?) {
        location?.let {
            MyApplication.getApp().setData(Constant.LOCATION_NAME,it.city)
            onLocation(it.city)
        }
    }
}