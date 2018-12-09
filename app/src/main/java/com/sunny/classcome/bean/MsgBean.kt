package com.sunny.classcome.bean

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 14:42
 */
data class MsgBean(
        var code: String,
        var msg: String,
        var content: Content?) {

    data class Content(
            var statu: String,
            var info: String,
            var data: Bean,
            var countStr: String,
            var countStrType: String,
            var remarks: String,
            var startIndex: String) {
        data class Bean(
                var pageSize: String,
                var pageIndex: String,
                var totalPage: String,
                var curPageRecords: String,
                var totalRecords: String,
                var dataList: ArrayList<Data>?,
                var countStr: String,
                var countStrType: String,
                var remarks: String,
                var startIndex: String) {

            data class Data(
                    var id: String,
                    var message: String,
                    var userId: String,
                    var msgType: String,
                    var createTime: String,
                    var title: String,
                    var isRead: String,
                    var userIdSender: String,
                    var courseId: String)
        }
    }
}