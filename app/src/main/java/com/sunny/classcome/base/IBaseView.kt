package com.sunny.classcome.base

import com.sunny.classcome.utils.ErrorViewType

interface IBaseView {

    fun showLoading()

    fun hideLoading()

    fun showError(errorType: ErrorViewType)

    fun hideError()

    fun showMessage(message:String)
}