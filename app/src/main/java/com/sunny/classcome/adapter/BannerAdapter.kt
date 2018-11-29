package com.sunny.classcome.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.bean.BannerBean
import com.sunny.classcome.utils.GlideUtil
import kotlinx.android.synthetic.main.layout_banner_imageview.view.*

class BannerAdapter(var context: Context, var list: ArrayList<BannerBean>) : PagerAdapter() {

    init {
        if (list.size >= 1) {
            list.add(0, list[list.size - 1])
            list.add(list[1])
        }

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = View.inflate(context, R.layout.layout_banner_imageview, null)
        GlideUtil.loadBanner(context, view.img_banner, list[position].photo)
        container.addView(view)
        return view
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].title
    }
}