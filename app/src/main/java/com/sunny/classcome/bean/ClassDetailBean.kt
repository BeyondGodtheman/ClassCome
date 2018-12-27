package com.sunny.classcome.bean

import java.io.Serializable

data class ClassDetailBean(
        var code: String,
        var msg: String,
        var content: Content) : Serializable {

    data class Content(
            var resCourseVO: ResCourseVO,
            var user: UserBean.Bean
    ) {
        data class ResCourseVO(
                var id: String,
                var createTime: String,
                var title: String,
                var materialList: ArrayList<ClassBean.Bean.Data.Material>,
                var price: String,//单节酬劳
                var sumPrice: String,//酬劳总价
                var courseNum: String,// 课程总节数
                var personType: String,//人员类型
                var classTime: ArrayList<String>?,//上课时段
                var classAddress: String,//上课地点
                var classDetailAdress: String,
                var startTime: String?,//课程日期开始时间
                var endTime: String?,//课程日期结束时间
                var category: ArrayList<String>?,//课程类别
                var description: String,//课程简介

                /**
                 * 是否已收藏：
                 * 1 已经收藏，
                 * 2 未收藏 ,
                 * 3 当前用户未登陆，未知是否收藏,
                 * -1 隐藏,
                 */
                var isFavorite: String,

                var isAppointment: String,
                var latitude: String,
                var longitude: String,
                var winningBidder: String,
                var state: String,
                var fatherCategoryName: String,
                var isAppraise: String,
                var course: ClassBean.Bean.Data.Course,
                var pintuanlist:ArrayList<PintuanResponseVO>
        ){
           data class PintuanResponseVO(
                    var pintuanInfo:PintuanInfo?, //拼团信息 ,
                    var shenyurenshu:String?, //拼团信息
                    var tuanyuan:ArrayList<Any>?,
                    var userName:String?,
                    var userPic:String?

            ){
               data class PintuanInfo(
                       var courseId:String?, //拼团课程id ,
                       var createTime:String?, //拼团创建时间 ,
                       var id:String?, // 拼团id
                       var maxPersonNum:String?, //拼团最大人数
                       var order:String?, //订单号 前台忽略
                       var parentId:String?, //拼团父id
                       var personNum:String?, //拼团人数但钱
                       var state:String?, //拼团状态 1 未生效, 2 已生效, 3 已结束
                       var userId:String? //拼团用户id
               )

           }
        }

    }
}