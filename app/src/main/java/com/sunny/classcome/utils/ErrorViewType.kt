package com.sunny.classcome.utils

/**
 * 错误View的类型枚举
 * Created by zhangye on 2018/8/2.
 */
data class ErrorViewType(
        var errorCode:String,
        var errorMessage:String,
        var errorIcon:Int = 0
)