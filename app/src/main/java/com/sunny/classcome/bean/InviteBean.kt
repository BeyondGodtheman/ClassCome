package com.sunny.classcome.bean

data class InviteBean(
        var code: String,
        var msg: String,
        var content: Bean?
){
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
            var startIndex: String){

        data class Data(
                var userName:String,
                var telephone:String,
                var userPic:String,
                var userId:String,
                var profession:String,
                var address:String,
                var isWelcome:String
        )
    }

}