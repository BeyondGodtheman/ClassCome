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
    val LOGIN = "login"

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

}