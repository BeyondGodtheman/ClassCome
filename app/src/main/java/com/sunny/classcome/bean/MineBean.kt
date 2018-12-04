package com.sunny.classcome.bean

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/5 01:26
 */
data class MineBean(
        var token: String?,
        var id: String?, //用户ID
        var userName: String?,
        var telephone: String?,
        var address: String?, // 地址
        var gradeName: String?, //用户等级
        var source: String?, //积分
        var userPic: String?,
        var authentication: String?,  //1代表个人，2代表企业
        var organization: String?, //企业名称(企业用户)
        var relation: String?, //联系人(企业用户
        var relationNum: String? //联系电话(企业用户)

)