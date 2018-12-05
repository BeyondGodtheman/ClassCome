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
import me.jessyan.autosize.utils.AutoSizeUtils


/**
 * 经典下拉刷新
 * Created by Gorden on 2017/6/17.
 */

class ClassicalHeader constructor(context: Context) : FrameLayout(context), KRefreshHeader {

    private val arrowImg: ImageView
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

        arrowImg = ImageView(context)
        arrowImg.setImageResource(R.mipmap.ic_krl_loading)
//        arrowImg.scaleType = ImageView.ScaleType.CENTER

        root.addView(arrowImg)

        textTitle = TextView(context)
        textTitle.textSize = AutoSizeUtils.pt2px(context,12f).toFloat()
        textTitle.text = "下拉刷新..."
        textTitle.setTextColor(Color.parseColor("#999999"))
        val params = LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.leftMargin = 20
        root.addView(textTitle, params)

        rotateAnimation.duration = 800
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.repeatMode = Animation.RESTART
        setPadding(0,  AutoSizeUtils.pt2px(context,30f), 0,  AutoSizeUtils.pt2px(context,7f))
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
//        arrowImg.setImageResource(R.mipmap.ic_arrow_down)
        textTitle.text = "下拉刷新..."
        isReset = true
        arrowImg.visibility = View.VISIBLE
    }

    override fun onPrepare(refreshLayout: KRefreshLayout) {
//        arrowImg.setImageResource(R.mipmap.ic_arrow_down)
        textTitle.text = "下拉刷新..."
    }

    override fun onRefresh(refreshLayout: KRefreshLayout) {
//        arrowImg.setImageResource(R.mipmap.ic_loading)
        arrowImg.startAnimation(rotateAnimation)
        textTitle.text = "加载中..."
        isReset = false
    }

    override fun onComplete(refreshLayout: KRefreshLayout, isSuccess: Boolean) {
        arrowImg.clearAnimation()
        arrowImg.visibility = GONE
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
                arrowImg.animate().rotation(-180f).start()
            } else if (percent < 1 && attain) {
                attain = false
                arrowImg.animate().rotation(0f).start()
                textTitle.text = "下拉刷新..."
            }
        }
    }
}