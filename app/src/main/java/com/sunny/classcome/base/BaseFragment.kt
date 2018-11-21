package com.sunny.classcome.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.sunny.classcome.R
import com.sunny.classcome.utils.ErrorViewType
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_base.view.*

abstract class BaseFragment : Fragment(), IBaseView, View.OnClickListener {
    private var savedInstanceState: Bundle? = null
    private var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.savedInstanceState = savedInstanceState
        mView = inflater.inflate(R.layout.fragment_base, container, false)
        mView?.iframeBody?.addView(inflater.inflate(setLayout(), container, false))
        initView()
        loadData()
        return mView
    }


    //沉浸式Title
    fun immersionTitle() {
        mView?.iframeBody?.apply {
            (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.BELOW, 0)
        }
        mView?.iframeTitle?.setBackgroundResource(R.color.transparent)

    }

    fun getLayoutView(): View? = mView

    fun getBaseActivity(): BaseActivity = activity as BaseActivity


    override fun onDestroy() {
        super.onDestroy()
        close()
    }


    override fun onResume() {
        super.onResume()
        update()
    }

    abstract fun setLayout(): Int

    abstract fun initView()

    abstract fun update()

    abstract fun loadData()


    override fun showMessage(message: String) {
        getBaseActivity().showMessage(message)
    }

    override fun showLoading() {
        getBaseActivity().hideLoading()
        hideLoading()
        iframeBody.addView(getBaseActivity().loadingView)
    }

    override fun hideLoading() {
        iframeBody.removeView(getBaseActivity().loadingView)
    }

    override fun showError(errorType: ErrorViewType) {
        hideError()
        getBaseActivity().hideError()
        iframeBody.addView(getBaseActivity().errorView)
    }

    override fun hideError() {
        iframeBody.removeView(getBaseActivity().errorView)
    }


    abstract fun close()
}