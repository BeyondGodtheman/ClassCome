package com.sunny.classcome.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.TranTypeAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.layout_default_title.view.*
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

class TranTypeActivity : BaseActivity() {

    var list = arrayListOf<ClassTypeBean>()
    //保存上次操作记录（用于取消操作）
    private var selectSet = HashSet<String>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        val titleView = titleManager.defaultTitle("课程分类", "确定", View.OnClickListener {
            MyApplication.getApp().setData(Constant.TRAN_TYPE, selectSet)
            setResult(2)
            finish()
        })

        titleView.ll_base_back.setOnClickListener {
            finish()
        }

        showTitle(titleView)

        refresh.setEnableRefresh(false)

        recl.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(v: View?) {

    }

    @Suppress("UNCHECKED_CAST")
    override fun loadData() {
        MyApplication.getApp().getData<HashSet<String>>(Constant.TRAN_TYPE, true)?.let {
            selectSet = it
        }

        val listOne = arrayListOf<ClassTypeBean.SubCategory>()
        listOne.add(ClassTypeBean.SubCategory("", "", "WIFI", ""))
        listOne.add(ClassTypeBean.SubCategory("", "", "停车场", ""))
        listOne.add(ClassTypeBean.SubCategory("", "", "空调", ""))
        listOne.add(ClassTypeBean.SubCategory("", "", "电视", ""))
        listOne.add(ClassTypeBean.SubCategory("", "", "音响", ""))
        listOne.add(ClassTypeBean.SubCategory("", "", "有线宽带", ""))
        list.add(ClassTypeBean("", "", "通用设施", "", listOne))

        val listTwo = arrayListOf<ClassTypeBean.SubCategory>()
        listTwo.add(ClassTypeBean.SubCategory("", "", "多方电话", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "投影仪", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "纸笔", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "签到台", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "演讲台", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "投影幕布", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "电子屏", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "白板", ""))
        listTwo.add(ClassTypeBean.SubCategory("", "", "茶歇", ""))
        list.add(ClassTypeBean("", "", "会议设施", "", listTwo))

        val listThree = arrayListOf<ClassTypeBean.SubCategory>()
        listThree.add(ClassTypeBean.SubCategory("", "", "舞台", ""))
        listThree.add(ClassTypeBean.SubCategory("", "", "游泳池", ""))
        listThree.add(ClassTypeBean.SubCategory("", "", "麻将桌", ""))
        list.add(ClassTypeBean("", "", "特殊设施", "", listThree))

        recl.adapter = TranTypeAdapter(list,selectSet)
    }
}