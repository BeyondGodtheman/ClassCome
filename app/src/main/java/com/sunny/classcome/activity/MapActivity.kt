package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amap.api.maps2d.CameraUpdate
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.CameraPosition
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_map.*
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.sunny.classcome.utils.ToastUtil


/**
 * Desc 查看地图
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/16 15:57
 */
class MapActivity : BaseActivity() {

    private var name = ""
    private var lon = "0"
    private var lan = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }


    override fun setLayout(): Int = R.layout.activity_map

    override fun initView() {
        showTitle(titleManager.defaultTitle("查看地图"))
        name = intent.getStringExtra("name")
        lon = intent.getStringExtra("lon")?:"0"
        lan = intent.getStringExtra("lan")?:"0"


        if (lon.isNotEmpty() && lan.isNotEmpty()){
            val latLng = LatLng(lan.toDouble(), lon.toDouble())

            mapView.map.addMarker(MarkerOptions().position(latLng).title(name))
            mapView.map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(latLng,20f,0f,0f)))
        }else{
            ToastUtil.show("暂无地理信息")
        }


    }

    override fun onClick(v: View?) {

    }


    override fun update() {
        super.update()
        mapView.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun close() {
        super.close()
        mapView.onDestroy()
    }

    companion object {
        fun start(context: Context,lan:String,lon:String,name:String){
            context.startActivity(Intent(context,MapActivity::class.java)
                    .putExtra("lan",lan)
                    .putExtra("lon",lon)
                    .putExtra("name",name))
        }
    }
}