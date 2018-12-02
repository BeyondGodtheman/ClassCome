package com.sunny.classcome.http

object Constant {

    fun isDebug(): Boolean = true

    var isLog = true //是否打印LOG

    const val CLIENT = "android"

    const val COMMON_UPLOADS = ""

    const val LOCATION_NAME = "locationName"

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
}

