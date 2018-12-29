package com.sunny.classcome.bean

data class ApplicantsBean(
        var code: String,
        var msg: String,
        var content: Bean?) {
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
                var state: String,
                var userName: String?,
                var telephone: String?,
                var userPic: String?,
                var createTime: String?,
                var userId: String?,
                var xgToken: String?)
    }

}