package com.sunny.classcome.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.sunny.classcome.R
import com.sunny.classcome.utils.ErrorViewType
import com.sunny.classcome.utils.TitleManager
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_base.view.*

abstract class BaseFragment : Fragment(), IBaseView, View.OnClickListener {
    private var savedInstanceState: Bundle? = null
    private var mView: View? = null
    val titleManager: TitleManager by lazy {
        getBaseActivity().titleManager
    }

    val loadingView: View by lazy {
        View.inflate(context, R.layout.layout_loading, null)
    }

    val errorView: View by lazy {
        View.inflate(context, R.layout.layout_error, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.savedInstanceState = savedInstanceState
        mView = inflater.inflate(R.layout.fragment_base, container, false)
        mView?.iframeBody?.addView(inflater.inflate(setLayout(), container, false))

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    //沉浸式Title
    fun immersionTitle() {
        mView?.iframeBody?.apply {
            (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.BELOW, 0)
        }
        mView?.iframeTitle?.setBackgroundResource(R.color.color_transparent)

    }

    fun getLayoutView(): View? = mView

    fun getBaseActivity(): BaseActivity = activity as BaseActivity


    abstract fun setLayout(): Int

    abstract fun initView()

    open fun close() {}

    open fun update() {}

    open fun loadData() {}

    fun showTitle(view: View): View {
        iframeTitle.visibility = View.VISIBLE
        iframeTitle.addView(view)
        return view
    }

    fun goneTitle(){
        iframeTitle.visibility = View.GONE
    }


    override fun showMessage(message: String) {
        getBaseActivity().showMessage(message)
    }

    override fun showLoading() {
        hideLoading()
        iframeBody.addView(loadingView)
    }

    override fun hideLoading() {
        iframeBody.removeView(loadingView)
    }

    override fun showError(errorType: ErrorViewType) {
        hideError()
       hideError()
        iframeBody.addView(errorView)
    }


    override fun hideError() {
        iframeBody.removeView(errorView)
    }


    override fun onResume() {
        super.onResume()
        update()
    }

    override fun onDestroy() {
        super.onDestroy()
        close()
    }

}