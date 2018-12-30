package com.sunny.classcome.base

/**
 * Desc
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/30 21:01
 */
data class BaseBeanContent<T> (
        var msg:String,
        var code:String,
        var content:T? = null
)