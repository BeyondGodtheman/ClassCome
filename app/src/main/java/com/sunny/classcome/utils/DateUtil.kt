package com.sunny.classcome.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun dateFormatYYMMdd(date: String): String {
        if (date.isEmpty()) {
            return ""
        }
        val dateFormat = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date(date.toLong()))
    }

    fun dateFormatYYMMddHHssmm(date: String): String {
        if (date.isEmpty()) {
            return ""
        }
        val dateFormat = SimpleDateFormat("yyy-MM-dd HH:ss:mm", Locale.getDefault())
        return dateFormat.format(Date(date.toLong()))
    }

    fun dateFormatHHssmm(date: String): String {
        if (date.isEmpty()) {
            return ""
        }
        val dateFormat = SimpleDateFormat("HH:ss:mm", Locale.getDefault())
        return dateFormat.format(Date(date.toLong()))
    }
}