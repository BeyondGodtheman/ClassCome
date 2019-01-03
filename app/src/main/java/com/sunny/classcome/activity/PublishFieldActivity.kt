package com.sunny.classcome.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.adapter.LabelAdapter
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_publish_field.*
import org.json.JSONArray

/**
 * Desc 发布场地
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/9 23:35
 */
class PublishFieldActivity : BaseActivity() {

    private var cityId = ""
    private var countyId = ""
    private var townId = ""
    private var latitude = ""
    private var longitude = ""

    private var classPid = ""

    override fun setLayout(): Int = R.layout.activity_publish_field

    //保存上次操作记录（用于取消操作）
    private var selectSet = HashSet<String>()

    private var subCategoryList = java.util.ArrayList<ClassTypeBean.SubCategory>()

    override fun initView() {
        showTitle(titleManager.defaultTitle("发布场地"))

        rl_class_type.setOnClickListener(this)
        txt_location.setOnClickListener(this)
        rl_tran.setOnClickListener(this)
        txt_publish.setOnClickListener(this)
        view_up.setHint("请描述一下您的场地服务情况")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_class_type -> {
                startActivityForResult(Intent(this, ClassTypeActivity::class.java)
                        .putExtra("pId", "40000"), 0)
            }
            R.id.txt_location -> {
                startActivityForResult(Intent(this, AddressActivity::class.java), 1)
            }
            R.id.rl_tran -> {
                MyApplication.getApp().setData(Constant.TRAN_TYPE, selectSet.clone())
                startActivityForResult(Intent(this, TranTypeActivity::class.java), 2)
            }
            R.id.txt_publish -> {
                publish()
            }
        }
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
            MyApplication.getApp().getData<ArrayList<ClassTypeBean.SubCategory>>(Constant.CLASS_TYPE, false)?.let { list ->
                val strSb = StringBuilder()
                subCategoryList = list
                list.forEach {
                    strSb.append(it.name).append(" ")
                }
                if (strSb.isNotEmpty()) {
                    strSb.deleteCharAt(strSb.lastIndex)
                }
                classPid = data?.getStringExtra("classPid") ?: ""
                txt_class_name.text = data?.getStringExtra("classPName") ?: ""
                txt_class_value.text = strSb
            }
        }


        if (requestCode == 2 && resultCode == 2) {
            selectSet = MyApplication.getApp().getData<HashSet<String>>(Constant.TRAN_TYPE, true)
                    ?: hashSetOf()

            val commondevice = arrayListOf<String>()
            val meetdevice = arrayListOf<String>()
            val specialdevice = arrayListOf<String>()

            selectSet.forEach {

                if (commondeviceList.contains(it)) {
                    commondevice.add(it)
                }

                if (meetdeviceList.contains(it)) {
                    meetdevice.add(it)
                }

                if (specialdeviceList.contains(it)) {
                    specialdevice.add(it)
                }
            }

            if (commondevice.isNotEmpty()) {
                recl_common.layoutManager = GridLayoutManager(this, 4)
                recl_common.adapter = LabelAdapter(commondevice)
            }
            if (meetdevice.isNotEmpty()) {
                recl_meeting.layoutManager = GridLayoutManager(this, 4)
                recl_meeting.adapter = LabelAdapter(meetdevice)
            }

            if (specialdevice.isNotEmpty()) {
                recl_special.layoutManager = GridLayoutManager(this, 4)
                recl_special.adapter = LabelAdapter(specialdevice)
            }
        }

        view_up.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }


    val commondeviceList = arrayListOf(
            "WIFI", "停车场", "空调", "电视", "音响", "有线宽带"
    )

    val meetdeviceList = arrayListOf(
            "多方电话", "投影仪", "纸笔", "签到台", "演讲台", "投影幕布", "电子屏", "白板", "茶歇"
    )

    val specialdeviceList = arrayListOf(
            "舞台", "游泳池", "麻将桌"
    )

    fun publish() {

        if (subCategoryList.isEmpty()) {
            ToastUtil.show("请选择场地分类！")
            return
        }

        if (edit_title.text.isEmpty()) {
            ToastUtil.show("请输入标题！")
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

        if (edit_cost.text.isEmpty()) {
            ToastUtil.show("请输入培训费用！")
            return
        }

        if (edit_hour.text.isEmpty()) {
            ToastUtil.show("请输入单次培训时间！")
            return
        }

        if (edit_people.text.isEmpty()) {
            ToastUtil.show("请输入场地容纳人数！")
            return
        }

        if (edit_square.text.isEmpty()) {
            ToastUtil.show("请输入场地空间!")
            return
        }

        if (txt_train_time.text.isEmpty()) {
            ToastUtil.show("请输入培训时间!")
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
        params["categoryIdList"] = categoryIdArray //类型ID

        params["coursetype"] = "4" //4:场地
        params["title"] = edit_title.text.toString()
        params["cityId"] = cityId
        params["countyId"] = countyId
        params["townId"] = townId
        params["classAddress"] = txt_location.text.toString()
        params["classDetailAdress"] = edit_street.text.toString()
        params["latitude"] = latitude
        params["longitude"] = longitude

        params["onecost"] = edit_cost.text.toString()
        params["oneallcost"] = edit_cost.text.toString()
        params["price"] = edit_cost.text.toString()
        params["sumPrice"] = edit_cost.text.toString()

        //团购参数结束
        params["onetime"] = edit_hour.text.toString() //单次培训时间
        params["captynum"] = edit_people.text.toString() //场地容纳人数
        params["workspace"] = edit_square.text.toString() //场地空间
        params["worktime"] = txt_train_time.text.toString() //场地时间

        val commondeviceSb = StringBuilder()
        val meetdeviceSb = StringBuilder()
        val specialdeviceSb = StringBuilder()

        selectSet.forEach {
            if (commondeviceList.contains(it)) {
                commondeviceSb.append(it).append(",")
            }

            if (meetdeviceList.contains(it)) {
                meetdeviceSb.append(it).append(",")
            }

            if (specialdeviceList.contains(it)) {
                specialdeviceSb.append(it).append(",")
            }
        }

        if (commondeviceSb.isNotEmpty()) {
            commondeviceSb.deleteCharAt(commondeviceSb.lastIndex)
        }
        if (meetdeviceSb.isNotEmpty()) {
            meetdeviceSb.deleteCharAt(meetdeviceSb.lastIndex)
        }
        if (specialdeviceSb.isNotEmpty()) {
            specialdeviceSb.deleteCharAt(specialdeviceSb.lastIndex)
        }

        params["commondevice"] = commondeviceSb //通用设备
        params["meetdevice"] = meetdeviceSb //会议设施
        params["specialdevice"] = specialdeviceSb //特殊设备
        params["description"] = view_up.getText() //描述

        val materialUrlArray = JSONArray()
        view_up.list.forEach {
            materialUrlArray.put(it.url)
        }
        params["materialUrlList"] = materialUrlArray

        showLoading()
        ApiManager.post(composites, params, Constant.COURSE_PUBLISHCOURSE, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (data.content?.statu == "1") {
                    PublishSuccessActivity.start(this@PublishFieldActivity,"4")
                    finish()
                } else {
                    ToastUtil.show(data.content?.info)
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }
        })
    }
}