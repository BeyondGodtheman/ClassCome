package com.sunny.classcome

import android.app.Application

class MyApplication : Application() {

    companion object {
        private lateinit var instance : MyApplication
        fun getApp(): MyApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}