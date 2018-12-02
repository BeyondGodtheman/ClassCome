package com.sunny.classcome.bean

/**
 * Desc 积分子条目
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 23:19
 */
data class PointBean(
        val desc: String,
        val point: String,
        val date: String = "1970.01.01"
)