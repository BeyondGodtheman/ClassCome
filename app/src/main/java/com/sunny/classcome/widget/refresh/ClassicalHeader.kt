package com.sunny.classcome.widget.refresh

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.utils.DensityUtil


/**
 * 经典下拉刷新
 * Created by Gorden on 2017/6/17.
 */

class ClassicalHeader constructor(context: Context) : FrameLayout(context), KRefreshHeader {

    private val arrawImg: ImageView
    private val textTitle: TextView

    private val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)


    private var isReset = true

    private var attain = false

    init {

        val root = LinearLayout(context)
        root.orientation = LinearLayout.HORIZONTAL
        root.gravity = Gravity.CENTER_VERTICAL
        addView(root, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        (root.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER

        arrawImg = ImageView(context)
        arrawImg.setImageResource(R.mipmap.ic_arrow_down)
        arrawImg.scaleType = ImageView.ScaleType.CENTER
        root.addView(arrawImg)

        textTitle = TextView(context)
        textTitle.textSize = 13f
        textTitle.text = "下拉刷新..."
        textTitle.setTextColor(Color.parseColor("#999999"))
        val params = LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.leftMargin = 20
        root.addView(textTitle, params)

        rotateAnimation.duration = 800
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.repeatMode = Animation.RESTART
        setPadding(0, DensityUtil.dip2px(15), 0, DensityUtil.dip2px(15))
    }

    override fun succeedRetention(): Long {
        return 200
    }

    override fun failingRetention(): Long {
        return 0
    }

    override fun refreshHeight(): Int {
        return height
    }

    override fun maxOffsetHeight(): Int {
        return 4 * height
    }

    override fun onReset(refreshLayout: KRefreshLayout) {
        arrawImg.setImageResource(R.mipmap.ic_arrow_down)
        textTitle.text = "下拉刷新..."
        isReset = true
        arrawImg.visibility = View.VISIBLE
    }

    override fun onPrepare(refreshLayout: KRefreshLayout) {
        arrawImg.setImageResource(R.mipmap.ic_arrow_down)
        textTitle.text = "下拉刷新..."
    }

    override fun onRefresh(refreshLayout: KRefreshLayout) {
        arrawImg.setImageResource(R.mipmap.ic_loading)
        arrawImg.startAnimation(rotateAnimation)
        textTitle.text = "加载中..."
        isReset = false
    }

    override fun onComplete(refreshLayout: KRefreshLayout, isSuccess: Boolean) {
        arrawImg.clearAnimation()
        arrawImg.visibility = GONE
        if (isSuccess) {
            textTitle.text = "刷新完成..."
        } else {
            textTitle.text = "刷新失败..."
        }
    }

    override fun onScroll(refreshLayout: KRefreshLayout, distance: Int, percent: Float, refreshing: Boolean) {
        if (!refreshing && isReset) {
            if (percent >= 1 && !attain) {
                attain = true
                textTitle.text = "释放刷新..."
                arrawImg.animate().rotation(-180f).start()
            } else if (percent < 1 && attain) {
                attain = false
                arrawImg.animate().rotation(0f).start()
                textTitle.text = "下拉刷新..."
            }
        }
    }
}