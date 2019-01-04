package com.sunny.classcome.activity

import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.utils.GlideApp
import kotlinx.android.synthetic.main.activity_guid.*
import kotlinx.android.synthetic.main.item_guide_photo.view.*

class GuideActivity : BaseActivity() {

    var imgArray = arrayListOf(
            R.drawable.bg_guide1,
            R.drawable.bg_guide2,
            R.drawable.bg_guide3
    )

    override fun setLayout(): Int = R.layout.activity_guid

    override fun initView() {

        goneTitle()

        viewPager.offscreenPageLimit = imgArray.size
        viewPager.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = LayoutInflater.from(this@GuideActivity).inflate(R.layout.item_guide_photo,container,false)
                GlideApp.with(this@GuideActivity)
                        .load(imgArray[position])
                        .into(view.img_photo)
                if(position == imgArray.size -1){
                    view.view_next.setOnClickListener {
                        startActivity(Intent(this@GuideActivity,HomeActivity::class.java))
                        finish()
                    }
                }else{
                    view.view_next.setOnClickListener {

                    }
                }
                container.addView(view)
                return view
            }

            override fun getCount(): Int = imgArray.size
        }
    }

    override fun onClick(v: View?) {

    }
}