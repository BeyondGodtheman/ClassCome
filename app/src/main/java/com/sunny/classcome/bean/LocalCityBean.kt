package com.sunny.classcome.bean

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 23:42
 */
data class LocalCityBean(
        var pinyin: String,
        var cityName: String) : Comparable<LocalCityBean> {

    override fun compareTo(other: LocalCityBean): Int {
        return other.pinyin.compareTo(this.pinyin)
    }

}