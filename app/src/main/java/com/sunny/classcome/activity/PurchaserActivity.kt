package com.sunny.classcome.activity

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.adapter.PurchaserAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.PurchaserBean
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

/**
 * Desc 购买者
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/3 23:04
 */
class PurchaserActivity : BaseActivity() {

    private val list: ArrayList<PurchaserBean> by lazy {
        arrayListOf(
                PurchaserBean("", "JoannChen", "800元", "13923457878", "2344 3435 3545", false),
                PurchaserBean("", "Eason", "800元", "13923457878", "2344 3435 3545", true),
                PurchaserBean("", "Justin", "500元", "18923457878", "2344 3435 7878", true)
        )

    }

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        showTitle(titleManager.defaultTitle("购买者"))

        recl.layoutManager = LinearLayoutManager(this)
        val adapter = PurchaserAdapter(list)
        recl.adapter = adapter

        // 最后一条view底部添加边距
        recl.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.getChildAdapterPosition(view) == ((parent.layoutManager?.itemCount ?: 0) - 1)) {
                    outRect.bottom = resources.getDimension(R.dimen.pt20).toInt()
                }
            }
        })
        adapter.setOnItemClickListener { view, index ->

        }

    }

    override fun onClick(v: View?) {
    }
}