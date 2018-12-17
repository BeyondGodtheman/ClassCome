package com.sunny.classcome.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.sunny.classcome.R
import java.io.File

object GlideUtil {

    private fun load(context: Context, url: String): GlideRequest<Drawable> {
        return GlideApp.with(context)
                .load(url)
                .centerCrop()
    }

    private fun load(context: Context, uri: Uri): GlideRequest<Drawable> {
        return GlideApp.with(context)
                .load(uri)
                .centerCrop()
    }

    private fun load(context: Context, file: File): GlideRequest<Drawable> {
        return GlideApp.with(context)
                .load(file)
                .centerCrop()
    }


    //加载banner
    fun loadBanner(context: Context, imageView: ImageView, url: String) {
        load(context, url).placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.color_nav_gray))).centerCrop().into(imageView)
    }

    //加载头像
    fun loadHead(context: Context, imageView: ImageView, url: String) {
        load(context, url).placeholder(R.mipmap.ic_default_head).centerCrop().into(imageView)
    }

    fun loadPhoto(context: Context, imageView: ImageView, url: String) {
        load(context, url).placeholder(R.drawable.bg_default_photo).centerCrop().into(imageView)
    }

    fun loadPhoto(context: Context, imageView: ImageView, uri: Uri) {
        load(context, uri).placeholder(R.drawable.bg_default_photo).centerCrop().into(imageView)
    }

    fun loadPhoto(context: Context, imageView: ImageView, file: File) {
        load(context, file).placeholder(R.drawable.bg_default_photo).centerCrop().into(imageView)
    }

    fun loadRes(context: Context, imageView: ImageView, url: String,res: Int) {
        load(context, url).placeholder(res).centerCrop().into(imageView)
    }

    fun loadDrawable(context: Context, imageView: ImageView, url: String,res: Drawable) {
        load(context, url).placeholder(res).centerCrop().into(imageView)
    }
}