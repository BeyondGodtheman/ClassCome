package com.sunny.classcome.base

/**
 * Desc
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/3 23:06
 */
data class PurchaserBean(
        val userHead: String,
        val userName: String,
        val payMoney: String,
        val contact: String,
        val verificationCode: String,
        val isFinish: Boolean
)