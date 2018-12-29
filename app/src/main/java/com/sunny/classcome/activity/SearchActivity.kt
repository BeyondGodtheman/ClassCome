package com.sunny.classcome.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.design.widget.TabLayout
import android.view.View
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassChildType
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.widget.popup.MoneyPopup
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : BaseActivity() {


    private var courseType = ""
    private var cityId = ""
    private var countyId = ""
    private var townId = ""
    private var pId = ""
    private var category = ""
    private var latitude = ""
    private var longitude = ""

    private var startPrice = ""

    private var endPrice = ""

    var subCategoryList = ArrayList<ClassTypeBean.SubCategory>()

    var moneyList = arrayListOf(
            "无限制", "100元内", "100-200元", "200-500元", "500元以上"
    )

    override fun setLayout(): Int = R.layout.activity_search

    override fun initView() {
        goneTitle()
        courseType = intent.getStringExtra("courseType") ?: ""
        pId = intent.getStringExtra("pId") ?: ""


        loadNav()
        ll_icon_back.setOnClickListener(this)
        rl_title.setOnClickListener(this)
        txt_money.setOnClickListener(this)
        rl_money.setOnClickListener(this)
        txt_address.setOnClickListener(this)
        rl_address.setOnClickListener(this)
        txt_date.setOnClickListener(this)
        rl_date.setOnClickListener(this)
        txt_search.setOnClickListener(this)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                category = subCategoryList[tab.position].id
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
            }
        })


        btn_search.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_icon_back -> {
                finish()
            }
            R.id.rl_title -> {

            }
            R.id.txt_money, R.id.rl_money -> {
                MoneyPopup(this, moneyList) {
                    when (it) {
                        0 -> {
                            startPrice = ""
                            endPrice = ""
                        }
                        1 -> {
                            startPrice = "1"
                            endPrice = "100"
                        }

                        2 -> {
                            startPrice = "100"
                            endPrice = "200"
                        }

                        3 -> {
                            startPrice = "200"
                            endPrice = "500"
                        }

                        4 -> {
                            startPrice = "500"
                            endPrice = "9999999"
                        }
                    }
                    txt_money.text = moneyList[it]
                }.showAsDropDown(txt_money)
            }
            R.id.txt_address, R.id.rl_address -> {
                startActivityForResult(Intent(this, AddressActivity::class.java)
                        .putExtra("search", true), 1)
            }

            R.id.txt_date, R.id.rl_date -> {
                val ca = Calendar.getInstance()
                val mYear = ca.get(Calendar.YEAR)
                val mMonth = ca.get(Calendar.MONTH)
                val mDay = ca.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    txt_date.text = ("$year-${month + 1}-$dayOfMonth")
                }, mYear, mMonth, mDay)
                datePickerDialog.setButton(-3, "无限制") { _: DialogInterface, i: Int ->
                    txt_date.text = "设置日期"
                }
                datePickerDialog.show()
            }

            R.id.btn_search,R.id.txt_search -> {
                val startDate = if (txt_date.text.toString() == "无限制") "" else txt_date.text.toString()
                val personType =
                        if (cbox_adult.isChecked && cbox_child.isChecked) {
                            "3"
                        } else if (cbox_adult.isChecked) {
                            "1"
                        } else if (cbox_child.isChecked) {
                            "2"
                        } else ""

                SearchResultActivity.start(this,edit_keyword.text.toString(),courseType, category, startPrice, endPrice, cityId, countyId, townId, startDate, personType)
            }

        }
    }


    private fun loadNav() {
        val params = HashMap<String, String>()
        params["pId"] = pId
        ApiManager.post(composites, params, Constant.COURSE_GETCATEGORY, object : ApiManager.OnResult<ClassChildType>() {
            override fun onSuccess(data: ClassChildType) {
                tabLayout.removeAllTabs()
                subCategoryList.clear()
                subCategoryList.addAll(data.content ?: arrayListOf())
                subCategoryList.forEach {
                    tabLayout.addTab(tabLayout.newTab().setText(it.name).setTag(it.id))
                }
            }

            override fun onFailed(code: String, message: String) {
            }
        })


    }

    companion object {
        fun start(context: Context, pId: String,courseType:String) {
            context.startActivity(Intent(context, SearchActivity::class.java)
                    .putExtra("pId", pId)
                    .putExtra("courseType",courseType))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == 1) {
            if (data?.getStringExtra("countyName") == null) {
                txt_address.text = "无限制"
            } else {
                txt_address.text = (data.getStringExtra("countyName") + " " + data.getStringExtra("townName"))
            }
            countyId = data?.getStringExtra("countyId") ?: ""
            townId = data?.getStringExtra("townId") ?: ""
            cityId = data?.getStringExtra("cityId") ?: ""
            latitude = data?.getStringExtra("latitude") ?: ""
            longitude = data?.getStringExtra("longitude") ?: ""
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}