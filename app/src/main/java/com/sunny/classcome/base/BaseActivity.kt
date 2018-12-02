package com.sunny.classcome.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.sunny.classcome.R
import com.sunny.classcome.utils.ErrorViewType
import com.sunny.classcome.utils.TitleManager
import com.sunny.classcome.utils.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_error.view.*

abstract class BaseActivity : AppCompatActivity(), IBaseView, View.OnClickListener {

    val composites: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val loadingView: View by lazy {
        View.inflate(this, R.layout.layout_loading, null)
    }

    val errorView: View by lazy {
        View.inflate(this, R.layout.layout_error, null)
    }

    val titleManager: TitleManager by lazy {
        TitleManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //强制屏幕
        setContentView(R.layout.activity_base)
        val bodyView = LayoutInflater.from(this).inflate(setLayout(), null, false)
        frameBody.addView(bodyView)

        loadingView.setOnClickListener { }
        errorView.setOnClickListener { }
        errorView.btnNext.setOnClickListener {
            hideError()
            loadData()
            update()
        }

        initView()
        loadData()
    }

    abstract fun setLayout(): Int

    abstract fun initView()

    open fun loadData() {}

    open fun update() {}

    open fun close() {}


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (this.currentFocus != null) {
                if (this.currentFocus?.windowToken != null) {
                    imm.hideSoftInputFromWindow(this.currentFocus?.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }
        return super.onTouchEvent(event)
    }


    /**
     * 隐藏输入法键盘
     */
    fun hideKeyboard() {
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun showTitle(view: View): View {
        frameTitle.visibility = View.VISIBLE
        frameTitle.addView(view)
        return view
    }

    fun goneTitle(){
        frameTitle.visibility = View.GONE
    }

    override fun showLoading() {
        hideLoading()
        loadingView.setOnClickListener { }

        frameBody.addView(loadingView)
    }

    override fun hideLoading() {
        frameBody.removeView(loadingView)
    }

    override fun showError(errorType: ErrorViewType) {
        hideError()
        frameBody.addView(errorView)
        errorView.setOnClickListener { }
        errorView.tvDesc.text = errorType.errorMessage
    }

    override fun hideError() {
        frameBody.removeView(errorView)
    }

    override fun showMessage(message: String) {
        ToastUtil.show(message)
    }

    override fun onResume() {
        super.onResume()
        update()
    }


    override fun onDestroy() {
        super.onDestroy()
        titleManager.onDestroy()
        close()
    }
}