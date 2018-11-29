package com.sunny.classcome.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.sunny.classcome.R

object GlideUtil {

    private fun load(context: Context, url: String): GlideRequest<Drawable> {
        return GlideApp.with(context)
                .load(url)
                .centerCrop()
    }


    //加载banner
    fun loadBanner(context: Context, imageView: ImageView, url: String) {
        load(context, url).placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.color_nav_gray))).into(imageView)
    }
}