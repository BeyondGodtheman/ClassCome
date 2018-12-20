package com.sunny.classcome.activity

import android.content.Intent
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.ClassTypeBean
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_publish_train.*

/**
 * Desc 发布培训
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/1 00:12
 */
class PublishTrainActivity : BaseActivity() {

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

    override fun setLayout(): Int = R.layout.activity_publish_train

    //保存上次操作记录（用于取消操作）
    private var subCategoryList = arrayListOf<ClassTypeBean>()

    override fun initView() {
        showTitle(titleManager.defaultTitle("发布培训"))

        txt_location.setOnClickListener(this)
        rl_tran.setOnClickListener(this)
        view_up.setHint("请描述一下您的场地服务情况")
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.txt_location ->{
                startActivityForResult(Intent(this, AddressActivity::class.java), 1)
            }
            R.id.rl_tran -> {
                if (subCategoryList.isNotEmpty()){
                    MyApplication.getApp().setData(Constant.TRAN_TYPE, subCategoryList)
                }
                startActivityForResult(Intent(this, TranTypeActivity::class.java), 2)
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

        if (requestCode ==2 && resultCode == 2){
            subCategoryList = ArrayList(MyApplication.getApp().getData<ArrayList<ClassTypeBean>>(Constant.TRAN_TYPE, true)?: arrayListOf())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun publish(){

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

        if (edit_cost.text.isEmpty()){
            ToastUtil.show("请输入培训费用！")
        }

        if (edit_hour.text.isEmpty()){
            ToastUtil.show("请输入单次培训时间！")
        }

        if (edit_people.text.isEmpty()){
            ToastUtil.show("请输入场地容纳人数！")
        }

        if (edit_square.text.isEmpty()){
            ToastUtil.show("请输入场地空间!")
        }

        if (txt_train_time.text.isEmpty()){
            ToastUtil.show("请输入培训时间!")
        }

        val params = hashMapOf<String, Any>()
        params["coursetype"] = "5" //5:培训
        params["title"] = edit_title.text.toString()
        params["cityId"] = cityId
        params["countyId"] = countyId
        params["townId"] = townId
        params["classAddress"] = txt_location.text.toString()
        params["classDetailAdress"] = edit_street.text.toString()
        params["onecost"] = edit_cost.text.toString()

        //团购参数开始
        params["assemcost"] =  edit_group_cost.text.toString()//拼团购买费用
        params["assemminnum"] = edit_people_start.text.toString() //拼团最小人数
        params["assemmaxnum"] = edit_people_end.text.toString() //拼团最大人数
        params["assemtime"] = edit_time_limit.text.toString() //拼团时间
        //团购参数结束

        params["onetime"] = edit_hour.text.toString() //单次培训时间
        params["captynum"] = edit_people.text.toString() //场地容纳人数
        params["workspace"] = edit_square.text.toString() //场地空间
        params["worktime"] = txt_train_time.text.toString() //场地时间


    }
}