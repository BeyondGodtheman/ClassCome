package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.fragment.MyClassFragment
import com.sunny.classcome.widget.dialog.SelectDialog
import kotlinx.android.synthetic.main.activity_my_class.*
import kotlinx.android.synthetic.main.layout_title_arrow.view.*

class MyClassActivity : BaseActivity() {

    private var type = 1

    //发布类型 1: 家教,2:代课,3:活动,4:场地,5:培训
    var courseType = "1"

    //课程状态(《待支付|已中标4》《进行中|待结算5》《完成6》《全部7》 )
    private val tabTitles = arrayListOf<String>()

    private val fragments = arrayListOf<MyClassFragment>()

    override fun setLayout(): Int = R.layout.activity_my_class

    private var typeName = "家教"

    private lateinit var titleView: View

    override fun initView() {

        type = intent.getIntExtra("type", 1)

        val title = if (type == 1) {
            tabTitles.add("全部")
            tabTitles.add("待支付")
            tabTitles.add("进行中")

            "发布的"
        } else {
            tabTitles.add("全部")
            tabTitles.add("已中标")
            tabTitles.add("待结算")
            "参与的"
        }
        tabTitles.add("评价/售后")

        titleView = titleManager.arrowTitle(title + typeName, View.OnClickListener { _ ->
            SelectDialog(this) { type ->
                courseType = type
                typeName = when (courseType) {
                    "1" -> "家教"
                    "2" -> "代课"
                    "3" -> "活动"
                    "4" -> "场地"
                    "5" -> "培训"
                    else -> ""
                }
                titleView.txt_arrow_title.text = (title + typeName)

                fragments.forEach {
                    it.setCourseType(courseType)
                    it.autoLoad()
                }

            }.show()
        })

        showTitle(titleView)


        fragments.add(MyClassFragment().setStatus(type.toString(), "7"))
        fragments.add(MyClassFragment().setStatus(type.toString(), "4"))
        fragments.add(MyClassFragment().setStatus(type.toString(), "5"))
        fragments.add(MyClassFragment().setStatus(type.toString(), "6"))

        view_pager.offscreenPageLimit = 4
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(index: Int): Fragment = fragments[index]

            override fun getCount(): Int = fragments.size

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitles[position]
            }
        }

        tabLayout.setupWithViewPager(view_pager)


    }

    override fun onClick(v: View) {

    }

    companion object {
        fun start(context: Context, type: Int) {
            context.startActivity(Intent(context, MyClassActivity::class.java)
                    .putExtra("type", type))
        }
    }
}