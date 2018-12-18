package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassChildType
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private var pId = ""

    override fun setLayout(): Int = R.layout.activity_search

    override fun initView() {
        goneTitle()
        pId = intent.getStringExtra("pId")
        loadNav()
        ll_icon_back.setOnClickListener(this)
        rl_title.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_icon_back -> {
                finish()
            }
            R.id.rl_title -> {

            }
        }
    }


    private fun loadNav() {
        val params = HashMap<String, String>()
        params["pId"] = pId
        ApiManager.post(composites, params, Constant.COURSE_GETCATEGORY, object : ApiManager.OnResult<ClassChildType>() {
            override fun onSuccess(data: ClassChildType) {
                tabLayout.removeAllTabs()
                data.content?.forEach {
                    tabLayout.addTab(tabLayout.newTab().setText(it.name).setTag(it.id))
                }
            }

            override fun onFailed(code: String, message: String) {
            }
        })


    }

    companion object {
        fun start(context: Context, pId: String) {
            context.startActivity(Intent(context, SearchActivity::class.java)
                    .putExtra("pId", pId))
        }
    }
}