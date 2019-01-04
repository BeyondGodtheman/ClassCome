package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_web

    override fun initView() {

        showTitle(titleManager.defaultTitle(intent.getStringExtra("title")))

//        webView.settings.javaScriptEnabled = true
//        webView.settings.javaScriptCanOpenWindowsAutomatically = true
//        webView.settings.allowFileAccess = true
//        webView.settings.domStorageEnabled = true// 打开本地缓存提供JS调用,至关重要
//        webView.settings.setAppCacheEnabled(true)
//        webView.settings.setAppCachePath(application.cacheDir.absolutePath)
//        webView.settings.databaseEnabled = true
//        webView.settings.textZoom = 50
//        webView.webViewClient = object : WebViewClient() {}
//        webView.webChromeClient = object : WebChromeClient() {}
//
        intent.getStringExtra("data")?.let {
            val html = "<html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no\">" +
                    "<meta http-equiv=Content-Type content=\"text/html; charset=gb2312\"></head><body style=\"margin:0;padding:0\">" + it + "</body></html>"
//            webView.loadDataWithBaseURL(null,html, "text/html", "gb2312",null)
            txt_Web.text = Html.fromHtml(html)
        }
    }

    override fun onClick(v: View?) {
    }

    companion object {
        fun start(context: Context, title: String, data: String) {
            context.startActivity(Intent(context, WebActivity::class.java)
                    .putExtra("title", title)
                    .putExtra("data", data))
        }
    }
}