package com.sunny.classcome

import android.app.Application
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

@Suppress("UNCHECKED_CAST")
class MyApplication : Application() {

    companion object {
        private lateinit var instance: MyApplication
        fun getApp(): MyApplication = instance
    }

    private val storeMap = HashMap<String, Any>() //内存数据存储

    override fun onCreate() {
        super.onCreate()
        instance = this
        AutoSizeConfig.getInstance().unitsManager
                .setSupportDP(false)
                .setSupportSP(false).supportSubunits = Subunits.PT
    }

    fun <T> getData(key: String, isDelete: Boolean): T? {

        if (!storeMap.containsKey(key)) {
            return null
        }

        val result = storeMap[key]

        if (isDelete) {
            storeMap.remove(key)
        }
        return result as T
    }


    /**
     * 存储数据
     */
    fun setData(key: String, t: Any?) {
        if (t != null) {
            storeMap[key] = t
        }
    }

}