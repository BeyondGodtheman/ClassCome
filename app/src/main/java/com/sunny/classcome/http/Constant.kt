package com.sunny.classcome.http

object Constant {

    fun isDebug(): Boolean = true

    var isLog = true //是否打印LOG

    const val CLIENT = "android"

    const val COMMON_UPLOADS = ""

    const val LOCATION_NAME = "locationName"

    const val LOGIN_PHONE = "loginPhone" //存储登录手机

    /**
     * 权限CODE
     */
    const val ALL = 1 //所有
    const val CAMERA = 2 //相机
    const val PHOTO = 3 //相册
    const val CROP = 4 //裁剪
    const val STORAGE = 5 //读写
    const val LOCATION = 6//定位
    const val PHONE = 7 //电话


    const val USER_REGISTERUSER = "user/registerUser" //用户注册

    const val USER_SENDCODEMSG ="user/sendCodeMsg" //注册获取验证码

    const val USER_SENDCODEOFSHORTCUT = "user/sendCodeOfShortcut" //快捷登录或找回密码发送验证码

    const val USER_RSETPASSWORD = "user/rsetPassword" //忘记密码修改

    const val USER_LOGINUSER = "user/loginUser" //用户登录

    const val USER_LOGINUSERBYTELEPHONECODE = "user/loginUserByTelePhoneCode" //快捷登录
}

