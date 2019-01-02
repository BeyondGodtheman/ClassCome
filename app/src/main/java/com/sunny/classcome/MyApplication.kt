package com.sunny.classcome

import android.app.Application
import android.content.Context
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.LogUtil
import com.sunny.classcome.utils.ToastUtil
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

@Suppress("UNCHECKED_CAST")
class MyApplication : Application() {

    val wxApi: IWXAPI by lazy {
        WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true)
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getApp(): MyApplication = instance

    }

    private val storeMap = HashMap<String, Any>() //内存数据存储


    override fun onCreate() {
        super.onCreate()
        instance = this

        wxApi.registerApp(Constant.WX_APP_ID)

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

    /**
     * 删除数据
     */
    fun removeData(key: String) {
        storeMap.remove(key)
    }

}