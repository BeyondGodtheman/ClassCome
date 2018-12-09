package com.sunny.classcome.bean


data class ClassBean(

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
                var course: Course,
                var materialList: ArrayList<Material>?,
                var categoryList: ArrayList<Category>?,
                var user: User,
                var order: Order,
                var isAppraise: String
        ) {
            data class Course(
                    var createTime: String,
                    var modifyTime: String,
                    var createUser: String,
                    var modifyUser: String,
                    var id: String,
                    var title: String,
                    var materialId: String,
                    var price: String,
                    var sumPrice: String,
                    var courseNum: String,
                    var dicId: String,
                    var personType: String,
                    var classTime: String,
                    var classAddress: String,
                    var classDetailAdress: String,
                    var startTime: String?,
                    var endTime: String?,
                    var audit: String,
                    var category: String,
                    var description: String,
                    var userId: String,
                    var latitude: String,
                    var longitude: String,
                    var distance: String,
                    var winningBidder: String,
                    var state: String,
                    var stateInfo: String,
                    var fatherCategoryName: String,
                    var pushPay: String,
                    var expirationTime: String,
                    var cityId: String,
                    var countyId: String,
                    var townId: String)

            data class Material(
                    var createTime: String,
                    var modifyTime: String,
                    var createUser: String,
                    var modifyUser: String,
                    var token: String,
                    var url: String,
                    var type: String,
                    var md5: String,
                    var userId: String,
                    var id: String,
                    var content: String)

            data class Category(
                    var id: String,
                    var pId: String,
                    var name: String,
                    var sort: String

            )

            data class User(
                    var userName: String?,
                    var telephone: String?,
                    var userPic: String?)

            data class Order(
                    var id: String?,
                    var orderNum: String?,
                    var state: String?,
                    var modeOfPayment: String?,
                    var paymentMoney: String?,
                    var createTime: String?,
                    var createBy: String?,
                    var courseId: String?,
                    var modifyOrderTime: String?,
                    var effectTime: String?,
                    var payFail: String?)
        }

    }
}
