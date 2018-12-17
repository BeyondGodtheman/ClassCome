package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.OrderDetailBean
import com.sunny.classcome.bean.WXPayBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.DateUtil
import com.sunny.classcome.utils.GlideUtil
import com.sunny.classcome.utils.ToastUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import kotlinx.android.synthetic.main.activity_pay.*

class PayActivity: BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_pay

    override fun initView() {
        showTitle(titleManager.defaultTitle("支付"))
        cbox_wx.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                cbox_al.isChecked = false
            }
        }

        cbox_al.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                cbox_wx.isChecked = false
            }
        }

        txt_commit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.txt_commit -> {
                if (cbox_wx.isChecked){
                    createWxOrder()
                }
            }
        }
    }

    override fun loadData() {
        showLoading()
        val params = HashMap<String, String>()
        params["id"] = intent.getStringExtra("id") ?: ""
        ApiManager.post(composites, params, Constant.ORDER_GETORDERDETAILNEW, object : ApiManager.OnResult<OrderDetailBean>() {
            override fun onSuccess(data: OrderDetailBean) {
                hideLoading()
                data.content?.materialList?.let {
                    if (it.isNotEmpty()){
                        GlideUtil.loadPhoto(this@PayActivity,img_class_photo,it[0].url?:"")
                    }
                }
                data.content?.order?.let {
                    txt_order_number.text = ("订单编号："+ it.orderNum)

                }
                data.content?.course?.let {
                    txt_class_name.text = it.title
                    txt_order_time.text = (DateUtil.dateFormatYYMMdd(it.createTime) +"至"+DateUtil.dateFormatYYMMdd(it.expirationTime))
                    txt_money.text = ("实付："+it.sumPrice)
                }
            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    companion object {
        fun start(context:Context,id:String){
            context.startActivity(Intent(context,PayActivity::class.java)
                    .putExtra("id",id))
        }
    }


    private fun createWxOrder(){
        showLoading()
        val params = HashMap<String, String>()
        params["id"] = intent.getStringExtra("id") ?: ""
//        params["pintuan"] = ""
        ApiManager.post(composites,params,Constant.ORDER_CREATEVCHARORDERSTR,object :ApiManager.OnResult<BaseBean<WXPayBean>>(){
            override fun onSuccess(data: BaseBean<WXPayBean>) {
                hideLoading()
                if (data.content?.statu == "1"){
                    data.content?.data?.let {
                        wxPay(it)
                    }
                }else{
                    ToastUtil.show(data.content?.info)
                }
            }

            override fun onFailed(code: String, message: String) {
            hideLoading()
            }

        })
    }


    //调用微信支付
    fun wxPay(wxPayBean: WXPayBean){
        val payReq = PayReq()
        payReq.appId = wxPayBean.appid
        payReq.partnerId = wxPayBean.partnerid
        payReq.prepayId = wxPayBean.prepayid
        payReq.packageValue = wxPayBean.`package`
        payReq.nonceStr = wxPayBean.noncestr
        payReq.timeStamp = wxPayBean.timestamp
        payReq.sign = wxPayBean.sign
        MyApplication.getApp().wxApi.sendReq(payReq)
    }
}