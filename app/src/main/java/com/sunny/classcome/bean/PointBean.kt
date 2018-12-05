package com.sunny.classcome.bean

/**
 * Desc 积分子条目
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/2 23:19
 */
data class PointBean(
        val code: String?,
        val msg: String?,
        val content: Bean?
) {
    data class Bean(
            var pageSize: String?,
            var pageIndex: String?,
            var totalPage: String?,
            var curPageRecords: String?,
            var totalRecords: String?,
            var dataList: ArrayList<Data>?) {

        data class Data(
                var id: String,
                var userId: String,
                var score: String,
                var createTime: String,
                var source: String)
    }
}