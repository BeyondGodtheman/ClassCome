package com.sunny.classcome.activity

import android.content.Intent
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.fragment.HomeFragment
import com.sunny.classcome.fragment.MineFragment
import com.sunny.classcome.fragment.PublishFragment
import com.sunny.classcome.utils.ToastUtil
import com.sunny.classcome.utils.UserManger
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


/**
 * Desc：
 * Author：JoannChen
 * Mail：yongzuo_chen@dingyuegroup.cn
 * Date：2018/11/21 0021 17:43
 */
class HomeActivity : BaseActivity() {

    private var currentPosition = 0


    private val fragmentList = ArrayList<Fragment>()


    private val homeFragment: HomeFragment by lazy {
        HomeFragment()
    }

    private val publishFragment: PublishFragment by lazy {
        PublishFragment()
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
        goneTitle()
        initViewPager()
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

        view_pager.adapter = FragmentAdapter(supportFragmentManager)
        view_pager.offscreenPageLimit = fragmentList.size

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                initTabView(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    /**
     * ViewPager的Adapter
     */
    inner class FragmentAdapter internal constructor(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

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


    fun initTabView(index: Int) {
        if (index != 0 && !UserManger.isLogin()) {
            startActivity(Intent(this, LoginActivity::class.java))
            initTabView(0)
            return
        }

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
            view.findViewById<View>(R.id.viewIcon)?.setBackgroundResource(icon)
            frameLayout.addView(view)
        }

        view_pager.currentItem = index
    }

    fun getCurrentIndext() = currentPosition


    private var mHits = LongArray(2)
    private fun doubleClick(): Boolean {
        System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
        mHits[mHits.size - 1] = SystemClock.uptimeMillis()
        if (mHits[0] >= SystemClock.uptimeMillis() - 800) {
            return true
        } else {
            ToastUtil.show("再按一次退出程序")
        }
        return false
    }

    override fun onBackPressed() {
        if (doubleClick()) {
            super.onBackPressed()
            System.exit(0)
        }
    }
}