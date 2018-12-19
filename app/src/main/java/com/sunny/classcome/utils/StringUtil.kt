package com.sunny.classcome.utils

import java.text.DecimalFormat


object StringUtil {

    //格式化钱保留两位小数
    fun formatMoney(money: Double):String{
        val format = DecimalFormat()
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
       return format.format(money)
    }

}