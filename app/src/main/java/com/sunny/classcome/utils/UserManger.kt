package com.sunny.classcome.utils

import com.sunny.classcome.bean.LoginBean
import com.sunny.classcome.http.ApiManager

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/5 00:45
 */
object UserManger {
    private const val LOGIN = "login"
    private const val CITY = "city"

    fun isLogin():Boolean{
        return getLogin() != null
    }

    fun setLogin(loginBean: LoginBean) {
        SharedUtil.setString(LOGIN, ApiManager.gSon.toJson(loginBean))
    }

    fun getLogin(): LoginBean? {
        val json = SharedUtil.getString(LOGIN)
        if (json.isEmpty()) {
            return null
        }
        return ApiManager.gSon.fromJson<LoginBean>(json, LoginBean::class.java)
    }


    //保存选择地址
    fun setAddress(id:String,name:String){
        SharedUtil.setString(CITY, "$id,$name")
    }

    //获取选择地址
    fun getAddress():String = SharedUtil.getString(CITY)
}