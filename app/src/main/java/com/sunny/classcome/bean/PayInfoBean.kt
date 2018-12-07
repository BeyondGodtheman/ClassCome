package com.sunny.classcome.bean

data class PayInfoBean(
        var token: String,
        var payType: String, //支付方式： 1支付宝 ， 2微信 ,
        var payId: String, //支付账号
        var realName: String, //用户名(支付宝)
        var openId: String, //支付账号
        var userName: String, //用户名
        var id: String)