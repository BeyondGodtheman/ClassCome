package com.sunny.classcome.bean

data class ClassDetailBean(

        var code: String,
        var msg: String,
        var content: ResCourseVO) {

    data class ResCourseVO(
            var id: String,
            var createTime: String,
            var title: String,
            var materialList: ArrayList<ClassBean.Bean.Data.Material>,
            var price: String,
            var sumPrice: String,
            var courseNum: String,
            var personType: String,
            var classTime: ArrayList<String>,
            var classAddress: String,
            var classDetailAdress: String,
            var startTime: String,
            var endTime: String,
            var category: ArrayList<String>,
            var description: String,
            var isFavorite: String,
            var isAppointment: String,
            var latitude: String,
            var longitude: String,
            var winningBidder: String,
            var state: String,
            var fatherCategoryName: String,
            var isAppraise: String,
            var course: ClassBean.Bean.Data.Course,
            var user: ClassBean.Bean.Data.User)
}