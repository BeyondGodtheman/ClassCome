package com.sunny.classcome.bean

data class WXPayBean(
        var code:String,
        var `package`:String,
        var appid:String,
        var sign:String,
        var prepayid:String,
        var partnerid:String,
        var noncestr:String,
        var info:String,
        var timestamp:String)