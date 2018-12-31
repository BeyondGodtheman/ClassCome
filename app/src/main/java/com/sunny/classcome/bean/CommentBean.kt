package com.sunny.classcome.bean

/**
 * Desc
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2019/1/1 01:43
 */
data class CommentBean (
        var pageSize:String?,
        var pageIndex:String?,
        var totalPage:String?,
        var curPageRecords:String,
        var totalRecords:String,
        var dataList:ArrayList<Data>?

){
    data class Data(
            var id:String?,
            var commentatorId:String?,
            var starLevel:String?,
            var description:String?,
            var token:String?,
            var createTime:String?,
            var courseId:String?,
            var userName:String?,
            var telephone:String?,
            var userPic:String?
    )


}

