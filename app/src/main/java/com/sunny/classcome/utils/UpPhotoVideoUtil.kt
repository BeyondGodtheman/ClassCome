package com.sunny.classcome.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import com.sunny.classcome.R
import com.sunny.classcome.bean.ClassBean
import kotlinx.android.synthetic.main.item_viewpager_profile.view.*

/**
 * Desc 上传图片视频工具类
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/16 17:46
 */
fun initPhotoVideo(context: Context, viewPager: ViewPager, list: ArrayList<ClassBean.Bean.Data.Material>) {
    viewPager.adapter = object : PagerAdapter() {
        override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

        override fun getCount(): Int = list.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(context).inflate(R.layout.item_viewpager_profile, container, false)
            GlideUtil.loadBanner(context, view.img_profile_photo, list[position].url
                    ?: "")
            if ((list[position].url ?: "").contains(".mp4")) {
                view.view_play.visibility = View.VISIBLE
            } else {
                view.view_play.visibility = View.GONE
            }

            view.setOnClickListener {
                val extension = MimeTypeMap.getFileExtensionFromUrl(list[position].url ?: "")
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
                val mediaIntent = Intent(Intent.ACTION_VIEW)
                mediaIntent.setDataAndType(Uri.parse(list[position].url ?: ""), mimeType)
                context.startActivity(mediaIntent)
            }

            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }
    }
}