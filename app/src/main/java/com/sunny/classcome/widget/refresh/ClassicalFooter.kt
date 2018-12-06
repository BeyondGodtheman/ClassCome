package com.sunny.classcome.widget.refresh

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
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


class ClassicalFooter: FrameLayout,KLoadMoreView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var recyclerView: KRecyclerView? = null
    private var arrowImg:ImageView
    private var textTitle: TextView

    private val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

    init {

        val root = LinearLayout(context)
        root.orientation = LinearLayout.HORIZONTAL
        root.gravity = Gravity.CENTER_VERTICAL
        addView(root, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        (root.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER

        arrowImg = ImageView(context)
        arrowImg.setImageResource(R.mipmap.ic_krl_loading)
        arrowImg.scaleType = ImageView.ScaleType.CENTER
        root.addView(arrowImg)

        textTitle = TextView(context)
        textTitle.textSize = AutoSizeUtils.pt2px(context,12f).toFloat()
        textTitle.text = "上拉或点击加载更多..."
        textTitle.setTextColor(Color.parseColor("#999999"))
        val params = LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.leftMargin = 20
        root.addView(textTitle, params)

        rotateAnimation.duration = 800
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.repeatMode = Animation.RESTART
        setPadding(0, AutoSizeUtils.pt2px(context,30f), 0,  AutoSizeUtils.pt2px(context,7f))

        setOnClickListener {
            recyclerView?.startLoadMore()
        }
    }


    override fun shouldLoadMore(recyclerView: KRecyclerView): Boolean {
        this.recyclerView = recyclerView
        return false
    }

    override fun onLoadMore(recyclerView: KRecyclerView) {
        arrowImg.visibility = VISIBLE
        arrowImg.startAnimation(rotateAnimation)
        textTitle.text = "正在加载..."
    }

    override fun onComplete(recyclerView: KRecyclerView, hasMore: Boolean) {
        arrowImg.clearAnimation()
        textTitle.text = if (hasMore) "上拉或点击加载更多..." else "没有更多数据"
        arrowImg.visibility = View.GONE
    }

    override fun onError(recyclerView: KRecyclerView, errorCode: Int) {
        arrowImg.clearAnimation()
        textTitle.text = "加载失败,点击重新加载"
        arrowImg.visibility = GONE
    }

    fun startAnim(){
        arrowImg.startAnimation(rotateAnimation)
    }
}