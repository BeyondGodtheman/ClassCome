package com.sunny.classcome.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_publish_class.*
import org.json.JSONArray
import java.util.*


/**
 * Desc 发布课程
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/9 23:35
 */
class PublishClassActivity : BaseActivity() {

    private var isEndData = false
    private var isEndTime = false
    private var startData = ""
    private var endData = ""
    private var startTime = ""
    private var endTime = ""
    private var cityId = ""
    private var countyId = ""
    private var townId = ""
    private var classPid = ""
    private var subCategoryList = ArrayList<ClassTypeBean.SubCategory>()
    override fun setLayout(): Int = R.layout.activity_publish_class

    override fun initView() {
        showTitle(titleManager.defaultTitle("发布课程"))

        rl_class_type.setOnClickListener(this)
        txt_class_date.setOnClickListener(this)
        txt_course_time.setOnClickListener(this)
        txt_location.setOnClickListener(this)

        view_up.setHint("请描述一下您的课程")
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.rl_class_type -> {
                startActivityForResult(Intent(this, ClassTypeActivity::class.java)
                        .putExtra("classPid",classPid)
                        .putExtra("classPName",txt_class_name.text.toString())
                        , 0)
            }

            R.id.txt_class_date -> {
                showDataPick()
            }
            R.id.txt_course_time -> {
                showTimePick()
            }
            R.id.txt_location -> {
                startActivityForResult(Intent(this, AddressActivity::class.java), 1)
            }

            R.id.txt_publish -> {
                publish()
            }
        }
    }



    //日期选择框
    private fun showDataPick() {
        val ca = Calendar.getInstance()
        val mYear = ca.get(Calendar.YEAR)
        val mMonth = ca.get(Calendar.MONTH)
        val mDay = ca.get(Calendar.DAY_OF_MONTH)

        if (!isEndData) {
            ToastUtil.show("请选择开始日期！")
        } else {
            ToastUtil.show("请选择结束日期！")
        }

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            if (isEndData) {
                isEndData = false
                endData = "$year-${month + 1}-$dayOfMonth"
                txt_class_date.text = ("${startData}至$endData")
            } else {
                isEndData = true
                startData = "$year-${month + 1}-$dayOfMonth"
                showDataPick()
            }
        }, mYear, mMonth, mDay)

        datePickerDialog.show()
    }

    //时间选择框
    private fun showTimePick() {

        if (!isEndTime) {
            ToastUtil.show("请选择开始时间！")
        } else {
            ToastUtil.show("请选择结束时间！")
        }

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            if (isEndTime) {
                isEndTime = false
                endTime = "${if (hourOfDay < 10) "0" else ""}$hourOfDay: ${if (minute < 10) "0" else ""}$minute"

                txt_course_time.text = ("${startTime}至$endTime")
            } else {
                isEndTime = true
                startTime = "${if (hourOfDay < 10) "0" else ""}$hourOfDay: ${if (minute < 10) "0" else ""}$minute"
                showTimePick()
            }

        }, 0, 0, true)
        timePickerDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == 1) {
            txt_location.text = (data?.getStringExtra("countyName") + " " + data?.getStringExtra("townName"))
            countyId = data?.getStringExtra("countyId") ?: ""
            townId = data?.getStringExtra("townId") ?: ""
        }

        if (requestCode == 0 && resultCode == 1) {
            classPid = data?.getStringExtra("classPid") ?: ""
            txt_class_name.text = data?.getStringExtra("classPName") ?: ""

            MyApplication.getApp().getData<ArrayList<ClassTypeBean.SubCategory>>(Constant.CLASS_TYPE, false)?.let { list ->
                val strSb = StringBuilder()
                subCategoryList = list
                list.forEach {
                    strSb.append(it.name).append(" ")
                }
                if (strSb.isNotEmpty()){
                    strSb.deleteCharAt(strSb.lastIndex)
                }
                txt_class_value.text = strSb
            }
        }

        view_up.onActivityResult(requestCode,resultCode,data)

        super.onActivityResult(requestCode, resultCode, data)
    }


    //发布
    private fun publish() {
        if (subCategoryList.isEmpty()){
            ToastUtil.show("请选择课程分类")
            return
        }


        val params = hashMapOf<String,Any>()
        val categoryIdArray = JSONArray()
        subCategoryList.forEach {
            categoryIdArray.put(it.id)
        }

        params["categoryIdList"] = categoryIdArray //类型ID

        ApiManager.post(composites,params,Constant.COURSE_PUBLISHCOURSE,object : ApiManager.OnResult<BaseBean<String>>(){
            override fun onSuccess(data: BaseBean<String>) {

            }

            override fun onFailed(code: String, message: String) {

            }

        })

    }

    override fun close() {
        MyApplication.getApp().removeData(Constant.CLASS_TYPE)
    }
}