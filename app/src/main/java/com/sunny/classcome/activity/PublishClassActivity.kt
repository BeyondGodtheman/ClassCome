package com.sunny.classcome.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
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
    private var latitude = ""
    private var longitude = ""
    private var coursetype = "2"
    private var subCategoryList = ArrayList<ClassTypeBean.SubCategory>()
    override fun setLayout(): Int = R.layout.activity_publish_class

    override fun initView() {

        coursetype = intent.getStringExtra("coursetype") ?: ""

        val title = when (coursetype) {
            "1" -> "家教"
            "2" -> "代课"
            "3" -> "活动"
            "4" -> "场地"
            "5" -> "培训"
            else -> ""
        }

        showTitle(titleManager.defaultTitle("发布$title"))

        rl_class_type.setOnClickListener(this)
        txt_class_date.setOnClickListener(this)
        txt_course_time.setOnClickListener(this)
        txt_location.setOnClickListener(this)
        txt_publish.setOnClickListener(this)

        edit_single_cost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var price = 0f
                edit_single_cost.text.toString().apply {
                    price = if (isEmpty()) {
                        0f
                    } else {
                        toFloat()
                    }
                }

                var total = 0
                edit_total_class.text.toString().apply {
                    total = if (isEmpty()) {
                        0
                    } else {
                        toInt()
                    }
                }
                edit_total_cost.setText((total * price).toString())
            }

        })

        view_up.setHint("请描述一下您的课程")

        view_up.showLoading = {
            showLoading()
        }

        view_up.hideLoading = {
            hideLoading()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.rl_class_type -> {
                startActivityForResult(Intent(this, ClassTypeActivity::class.java)
                        .putExtra("classPid", classPid)
                        .putExtra("classPName", txt_class_name.text.toString())
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
            cityId = data?.getStringExtra("cityId") ?: ""
            latitude = data?.getStringExtra("latitude") ?: ""
            longitude = data?.getStringExtra("longitude") ?: ""
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
                if (strSb.isNotEmpty()) {
                    strSb.deleteCharAt(strSb.lastIndex)
                }
                txt_class_value.text = strSb
            }
        }

        view_up.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }


    //发布
    private fun publish() {
        if (subCategoryList.isEmpty()) {
            ToastUtil.show("请选择课程分类！")
            return
        }

        if (!cbox_adult.isChecked && !cbox_child.isChecked) {
            ToastUtil.show("请选择适应人群！")
            return
        }

        if (edit_title.text.isEmpty()) {
            ToastUtil.show("请输入课程标题！")
            return
        }

        if (startData.isEmpty() || endData.isEmpty()) {
            ToastUtil.show("请选择课程时间！")
            return
        }

        if (startTime.isEmpty() || endTime.isEmpty()) {
            ToastUtil.show("请选择上课时段！")
            return
        }


        if (countyId.isEmpty() || townId.isEmpty()) {
            ToastUtil.show("请选择所在地区！")
            return
        }

        if (edit_street.text.isEmpty()) {
            ToastUtil.show("请输入详细地址！")
            return
        }

        if (edit_total_class.text.isEmpty()) {
            ToastUtil.show("请输入课程总节数！")
            return
        }

        if (edit_recruit_people.text.isEmpty()) {
            ToastUtil.show("请输入招聘总人数")
            return
        }

        if (edit_single_cost.text.isEmpty()) {
            ToastUtil.show("请输入单节酬劳")
            return
        }

        if (view_up.list.isEmpty()) {
            ToastUtil.show("请上传图片或视屏")
            return
        }

        val params = hashMapOf<String, Any>()
        val categoryIdArray = JSONArray()
        subCategoryList.forEach {
            categoryIdArray.put(it.id.toInt())
        }
        params["coursetype"] = coursetype //2:代课
        params["categoryIdList"] = categoryIdArray //类型ID

        if (cbox_child.isChecked && cbox_adult.isChecked) {
            params["personType"] = "3"
        } else {
            if (cbox_child.isChecked) {
                params["personType"] = "2"
            }

            if (cbox_adult.isChecked) {
                params["personType"] = "1"
            }
        }

        params["title"] = edit_title.text.toString()
        params["startTime"] = startData
        params["endTime"] = endData

        val classTimeArray = JSONArray()
        classTimeArray.put("$startTime-$endTime")
        params["classTime"] = classTimeArray
        params["cityId"] = cityId
        params["countyId"] = countyId
        params["townId"] = townId
        params["classAddress"] = txt_location.text.toString()
        params["classDetailAdress"] = edit_street.text.toString()
        params["courseNum"] = edit_total_class.text.toString()
        params["onecost"] = edit_single_cost.text.toString()
        params["price"] = edit_single_cost.text.toString()
        params["sumPrice"] = edit_total_cost.text.toString()
        params["publishTotal"] = edit_recruit_people.text.toString() //招聘人数
        params["description"] = view_up.getText() //描述
        params["latitude"] = latitude
        params["longitude"] = longitude


        val materialUrlArray = JSONArray()
        view_up.list.forEach {
            materialUrlArray.put(it.url)
        }
        params["materialUrlList"] = materialUrlArray


        ApiManager.post(composites, params, Constant.COURSE_PUBLISHCOURSE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                if (data.content?.statu == "1") {
                    startActivity(Intent(this@PublishClassActivity, PublishSuccessActivity::class.java))
                    finish()
                } else {
                    ToastUtil.show(data.content?.info)
                }
            }

            override fun onFailed(code: String, message: String) {

            }

        })

    }

    override fun close() {
        MyApplication.getApp().removeData(Constant.CLASS_TYPE)
    }


    companion object {
        fun start(context: Context, coursetype: String) {
            context.startActivity(Intent(context, PublishClassActivity::class.java)
                    .putExtra("coursetype", coursetype))
        }
    }
}