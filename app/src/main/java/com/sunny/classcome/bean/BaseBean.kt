package com.sunny.classcome.bean

/**
 * 公共实体类
 * Created by 张野 on 2017/9/14.
 */

class BaseBean<T> {
    var msg = ""
    var code = "200"
    var content: Content<T>? = null
    var type = javaClass

    override fun toString(): String {
        return "BaseBean(msg='$msg', code='$code', content=$content, type=$type)"
    }

    class Content<T> {
        var statu = ""
        var info = ""
        var data: T? = null
    }
}