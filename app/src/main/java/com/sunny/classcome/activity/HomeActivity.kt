package com.sunny.classcome.activity

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_bookshelf_header.*
import java.util.*


/**
 * Desc：
 * Author：JoannChen
 * Mail：yongzuo_chen@dingyuegroup.cn
 * Date：2018/11/21 0021 17:43
 */
class HomeActivity : BaseActivity() {

    private var currentPosition = 0

    private val homePage = 0
    private val publishPage = 1
    private val minePage = 2

    private val fragmentList = ArrayList<Fragment>()

    internal var scrollOffset = 0f
    private val statusNoramlColor = Color.parseColor("#1098AE")
    var blendCorlor = Color.parseColor("#1098AE")

    private val headerNormalHeight by lazy {
        resources.getDimensionPixelSize(0)
    }

    private val homeFragment: MineFragment by lazy {
        MineFragment()
    }

    private val publishFragment: MineFragment by lazy {
        MineFragment()
    }

    private val mineFragment: MineFragment by lazy {
        MineFragment()
    }

    private val homeNavText: Array<String> by lazy {
        arrayOf(
                resources.getString(R.string.nav_home),
                resources.getString(R.string.nav_publish),
                resources.getString(R.string.nav_mine))
    }

    private val homeNavIcon: Array<Int> by lazy {
        arrayOf(
                R.mipmap.ic_nav_home_gray,
                R.mipmap.ic_nav_add_gray,
                R.mipmap.ic_nav_mine_gray)
    }

    private val homeNavIconSelect: Array<Int> by lazy {
        arrayOf(
                R.mipmap.ic_nav_home_blue,
                R.mipmap.ic_nav_add_blue,
                R.mipmap.ic_nav_mine_blue)
    }

    private val frameLayouts: Array<FrameLayout> by lazy {
        arrayOf(
                flHome,
                flAdd,
                flMine)
    }


    override fun setLayout(): Int = R.layout.activity_home


    override fun onClick(v: View) {
        when (v.id) {
            R.id.flHome -> initTabView(0)
            R.id.flAdd -> initTabView(1)
            R.id.flMine -> initTabView(2)
        }
    }


    override fun initView() {
        initRecyclerView()
        initViewPager()
        changeNavigationState(currentPosition)


        initTabView(0)



        flHome.setOnClickListener(this)
        flAdd.setOnClickListener(this)
        flMine.setOnClickListener(this)
    }

    private fun initViewPager() {

        fragmentList.clear()
        fragmentList.add(homeFragment)
        fragmentList.add(publishFragment)
        fragmentList.add(mineFragment)

        view_pager?.adapter = fragmentAdapter(supportFragmentManager)
        view_pager?.offscreenPageLimit = fragmentList.size

        view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                changeNavigationState(currentPosition)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    private fun initRecyclerView() {

        recycler_view.recycledViewPool.setMaxRecycledViews(0, 12)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.isFocusable = false
        recycler_view.itemAnimator?.addDuration = 0
        recycler_view.itemAnimator?.changeDuration = 0
        recycler_view.itemAnimator?.moveDuration = 0
        recycler_view.itemAnimator?.removeDuration = 0

        (recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

//        recycler_view.adapter = bookShelfAdapter

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                changeTitleBarBackground(dy)

            }
        })
    }


    /**
     * 设置导航栏选中状态
     */
    private fun changeNavigationState(position: Int) {
//        txt_home?.isSelected = position == homePage
//        txt_publish?.isSelected = position == publishPage
//        txt_mine?.isSelected = position == minePage
    }

    /**
     * 点击导航栏选项
     */
    private fun clickNavigationItem(position: Int) {
        if (currentPosition == position) {
            return
        }

        view_pager?.currentItem = position
        currentPosition = position

        changeNavigationState(currentPosition)
    }

    /**
     * 根据偏移量改变TitleBar颜色
     */
    private fun changeTitleBarBackground(scrollY: Int) {
        if (headerNormalHeight != 0) {
            scrollOffset += scrollY.toFloat()
            if (scrollOffset > headerNormalHeight) {
                scrollOffset = headerNormalHeight.toFloat()
            } else if (scrollOffset < 0) {
                scrollOffset = 0f
            }

            val ratio = scrollOffset / headerNormalHeight
            blendCorlor = ColorUtils.blendARGB(Color.parseColor("#1098AE"), Color.WHITE, ratio)
            layout_title.setBackgroundColor(blendCorlor)

            val textColor = ColorUtils.blendARGB(Color.WHITE, Color.BLACK, ratio)
            txt_bookshelf.setTextColor(textColor)

            //icon透明度
            img_head_more2.alpha = ratio
            img_head_more.alpha = 1 - ratio

            img_head_search2.alpha = ratio
            img_head_search.alpha = 1 - ratio

            img_download_manager2.alpha = ratio
            img_download_manager.alpha = 1 - ratio

            img_head_setting2.alpha = ratio
            img_head_setting.alpha = 1 - ratio
        }
    }


    /**
     * ViewPager的Adapter
     */
    inner class fragmentAdapter internal constructor(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment? {
            return fragmentList[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }


    private fun initTabView(index: Int) {

        frameLayouts.forEachIndexed { i, frameLayout ->
            frameLayout.removeAllViews()

            var layout = R.layout.layout_nav_home_gray
            var color = R.color.color_nav_gray
            var icon = homeNavIcon[i]

            if (i == index) {
                layout = R.layout.layout_nav_home_blue
                color = R.color.color_nav_blue
                icon = homeNavIconSelect[i]
            }
            val view = View.inflate(this, layout, null)
            view.findViewById<TextView>(R.id.tvName)?.apply {
                text = homeNavText[i]
                setTextColor(ContextCompat.getColor(context, color))
            }
            view.findViewById<View>(R.id.vIcon)?.setBackgroundResource(icon)
            frameLayout.addView(view)
        }
    }
}