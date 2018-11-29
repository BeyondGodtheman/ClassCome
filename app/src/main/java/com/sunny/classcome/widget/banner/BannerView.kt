package com.sunny.classcome.widget.banner

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.sunny.classcome.R
import com.sunny.classcome.adapter.BannerAdapter
import com.sunny.classcome.bean.BannerBean
import kotlinx.android.synthetic.main.layout_banner.view.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class BannerView : RelativeLayout {
    private var delay = 3 * 1000
    private var job: Job? = null
    private var pointList = arrayListOf<View>()
    private var adapter: BannerAdapter? = null
    private var currentIndex = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            var currentPosition = 0
            override fun onPageScrollStateChanged(state: Int) {
                if (currentPosition >= view_pager.offscreenPageLimit - 1) {
                    view_pager.setCurrentItem(1, false)
                }
                if (currentPosition <= 0) {
                    view_pager.setCurrentItem(view_pager.offscreenPageLimit - 2, false)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                pointList[currentIndex].alpha = 0.3F
                pointList[currentIndex].layoutParams = LinearLayout.LayoutParams(resources.getDimension(R.dimen.dp14).toInt(), resources.getDimension(R.dimen.dp14).toInt())
                        .apply { leftMargin = resources.getDimension(R.dimen.dp15).toInt() }

                currentIndex = currentPosition - 1

                if (currentIndex > pointList.size - 1) {
                    currentIndex = 0
                }

                if (currentIndex < 0) {
                    currentIndex = pointList.size - 1
                }

                pointList[currentIndex].alpha = 1F
                pointList[currentIndex].layoutParams = LinearLayout.LayoutParams(resources.getDimension(R.dimen.dp58).toInt(), resources.getDimension(R.dimen.dp14).toInt())
                        .apply { leftMargin = resources.getDimension(R.dimen.dp15).toInt() }
            }
        })

        view_pager.setOnTouchListener { _: View, motionEvent: MotionEvent ->

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    stopLoop()
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    startLoop()
                }

            }

            return@setOnTouchListener false
        }

    }

    fun loadData(list: ArrayList<BannerBean>) {
        stopLoop()
        currentIndex = 0
        pointList.clear()
        llPoint.removeAllViews()

        if (list.isEmpty()) {
            if (adapter != null) {
                adapter = BannerAdapter(context, list)
                view_pager.adapter = adapter
            }
            return
        }

        for (i in 0 until list.size) {
            val view = View(context)
            view.alpha = 0.3F
            view.setBackgroundResource(R.drawable.bg_banner_point)
            LinearLayout.LayoutParams(resources.getDimension(R.dimen.dp14).toInt(), resources.getDimension(R.dimen.dp14).toInt()).apply {
                leftMargin = 15
                llPoint.addView(view, this)
            }
            pointList.add(view)
        }

        pointList[0].alpha = 1F

        adapter = BannerAdapter(context, list)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = list.size
        view_pager.currentItem = 1
        startLoop()
    }


    fun startLoop() {

        job?.cancel()

        job = launch(CommonPool) {
            while (true) {
                delay(delay)

                launch(UI) {
                    view_pager.setCurrentItem(view_pager.currentItem++, true)
                }
            }
        }
    }

    fun stopLoop() {
        job?.cancel()
    }

}