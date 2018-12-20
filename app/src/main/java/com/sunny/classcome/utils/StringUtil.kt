package com.sunny.classcome.utils

import java.text.DecimalFormat


object StringUtil {

    //格式化钱保留两位小数
    fun formatMoney(money: Double):String{
        val format = DecimalFormat("0.00")
       return format.format(money)
    }

}