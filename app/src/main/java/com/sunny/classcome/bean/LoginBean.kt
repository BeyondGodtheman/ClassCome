package com.sunny.classcome.bean

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/4 22:53
 */
data class LoginBean(
        var msg: String,
        var code: String,
        var content: Bean) {

    data class Bean(
            var statu: String,
            var token: String,
            var info: String,
            var isBindPhone: String,
            var userId: String)
}