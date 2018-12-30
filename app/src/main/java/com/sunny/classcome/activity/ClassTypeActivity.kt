package com.sunny.classcome.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.ClassTypeAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.base.BaseBeanContent
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_default_title.view.*
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

class ClassTypeActivity : BaseActivity() {

    val list = arrayListOf<ClassTypeBean>()
    val adapter: ClassTypeAdapter by lazy {
        ClassTypeAdapter(list, selectMap)
    }

    var pId = "0"

    private val selectMap = hashMapOf<String, ArrayList<ClassTypeBean.SubCategory>>()
    //保存上次操作记录（用于取消操作）
    private var subCategoryList = arrayListOf<ClassTypeBean.SubCategory>()

    override fun setLayout(): Int = R.layout.layout_refresh_recycler

    override fun initView() {
        val titleView = titleManager.defaultTitle("课程分类", "确定", View.OnClickListener {

            if (selectMap.isEmpty()) {
                ToastUtil.show("至少选择一个分类")
                return@OnClickListener
            }

            val intent = Intent()
            intent.putExtra("classPid", selectMap.keys.last())
            intent.putExtra("classPName", list.find { it.id == intent.getStringExtra("classPid") }?.name)
            setResult(1, intent)
            MyApplication.getApp().setData(Constant.CLASS_TYPE, selectMap.values.last())
            finish()
        })

        titleView.ll_back.setOnClickListener {
            val intent = Intent()
            intent.putExtra("classPid", this@ClassTypeActivity.intent.getStringExtra("classPid"))
            intent.putExtra("classPName", this@ClassTypeActivity.intent.getStringExtra("classPName"))
            setResult(1, intent)
            MyApplication.getApp().setData(Constant.CLASS_TYPE, subCategoryList)
            finish()
        }

        showTitle(titleView)

        pId = intent.getStringExtra("pId")?:"0"

        intent.getStringExtra("classPid")?.let { s ->
            MyApplication.getApp().getData<ArrayList<ClassTypeBean.SubCategory>>(Constant.CLASS_TYPE, false)?.let {
                if (s.isNotEmpty()) {
                    subCategoryList.clear()
                    subCategoryList.addAll(it)
                    selectMap[s] = it
                }
            }
        }

        refresh.setRefreshHeader(ClassicsHeader(this))

        refresh.setOnRefreshListener {
            loadData()
        }

        recl.layoutManager = LinearLayoutManager(this)
        recl.adapter = adapter
    }

    override fun onClick(v: View?) {

    }

    override fun loadData() {
        showLoading()
        if (pId == "0"){
            ApiManager.post(composites, null, Constant.COURSE_GETCATEGORYALL, object : ApiManager.OnResult<BaseBean<ArrayList<ClassTypeBean>>>() {
                override fun onSuccess(data: BaseBean<ArrayList<ClassTypeBean>>) {
                    refresh.finishRefresh()
                    hideLoading()
                    list.clear()
                    list.addAll(data.content?.data ?: arrayListOf())

                    adapter.notifyDataSetChanged()
                }

                override fun onFailed(code: String, message: String) {
                    hideLoading()
                    refresh.finishRefresh()
                }
            })
        }else{
            ApiManager.post(composites, null, Constant.COURSE_GETCATEGORY, object : ApiManager.OnResult<BaseBeanContent<ArrayList<ClassTypeBean.SubCategory>>>() {
                override fun onSuccess(data: BaseBeanContent<ArrayList<ClassTypeBean.SubCategory>>) {
                    refresh.finishRefresh()
                    hideLoading()
                    list.clear()
                    data.content?.filter {it.id == pId}?.forEach { subCategory ->
                        list.add(ClassTypeBean(subCategory.id,subCategory.pId,subCategory.name,subCategory.sort,
                                data.content?.filter { it.pId == subCategory.id } as ArrayList<ClassTypeBean.SubCategory>))
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailed(code: String, message: String) {
                    hideLoading()
                    refresh.finishRefresh()
                }
            })

        }

    }
}