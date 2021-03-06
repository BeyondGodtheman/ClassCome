package com.sunny.classcome.bean

data class UserBean(
        var user: Bean?,
        var materialList: ArrayList<ClassBean.Bean.Data.Material>?) {
    data class Bean(
            var createTime: String,
            var modifyTime: String,
            var createUser: String,
            var modifyUser: String,
            var token: String,
            var unionId: String,
            var openId: String,
            var id: String,
            var userName: String,
            var passWord: String,
            var telephone: String,
            var sex: String,
            var address: String,
            var cardFront: String,
            var cardBack: String,
            var identityCard: String,
            var authentication: String,
            var userPic: String?,
            var isDel: String,
            var authCode: String,
            var organization: String,
            var relation: String,
            var relationNum: String,
            var relationimgId: String,
            var isAuth: String,
            var age: Int,
            var materialId: String,
            var publishNum: String,
            var teachingNum: String,
            var violateNum: String,
            var profession: String,
            var speciality: String,
            var workAge: Int,
            var isRemind: String,
            var registerState: String,
            var codeTime: String,
            var loginTimes: String,
            var grade: String,
            var userInfo: String,
            var status: String,
            var source: Int,
            var gradeName: String,
            var loginDate: String,
            var xgToken: String,
            var payType: String,
            var realName: String,
            var payId: String,
            var weChatUser: String)
}