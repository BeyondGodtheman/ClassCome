package com.sunny.classcome.base

import com.sunny.classcome.utils.ErrorViewType
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T:IBaseView>(var view: T?) {

    val composites = CompositeDisposable()

    abstract fun onCreate()

    abstract fun onClose()

    fun onDestroy(){
        composites.dispose()
        onClose()
    }

    fun showError(code:String, message:String){
        val errorViewType = ErrorViewType(code,message)
        when(code){
            "0" -> view?.showError(errorViewType)
        }

    }

    fun hideError(){
        view?.hideError()
    }

    fun showMessage(message: String){
        view?.showMessage(message)
    }

    fun showLoading(){
        view?.showLoading()
    }

    fun hideLoading(){
        view?.hideLoading()
    }
}