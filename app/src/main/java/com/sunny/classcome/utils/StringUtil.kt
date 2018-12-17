package com.sunny.classcome.utils

import java.text.DecimalFormat

object StringUtil {

    //格式化钱保留两位小数
    fun formatMoney(money: Float):String{
        val format = DecimalFormat("##.00")
       return format.format(money)
    }

}