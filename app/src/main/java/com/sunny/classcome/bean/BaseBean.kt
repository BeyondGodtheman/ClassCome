package com.sunny.classcome.bean

/**
 * 公共实体类
 * Created by 张野 on 2017/9/14.
 */

class BaseBean<T> {
    var result = ""
    var status = "200"
    var message: T? = null
    var type = javaClass

    override fun toString(): String =
            "BaseModel(result='$result', status=$status, message=$message, value=$type)"
}