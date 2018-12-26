package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import com.alipay.sdk.app.PayTask
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.*
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import com.tencent.mm.opensdk.modelpay.PayReq
import kotlinx.android.synthetic.main.activity_pay.*
import org.greenrobot.eventbus.EventBus


@Suppress("UNCHECKED_CAST")
class PayActivity: BaseActivity() {

    private val SDK_PAY_FLAG = 1

    private var type = 1

    private val handler = Handler{

        when(it.what){
            SDK_PAY_FLAG -> {
                val payResult = PayResult(it.obj as Map<String, String>)
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                val resultInfo = payResult.getResult()// 同步返回需要验证的信息
                val resultStatus = payResult.getResultStatus()
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    ToastUtil.show("支付成功！")
                    EventBus.getDefault().post(Pay())
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    ToastUtil.show("支付失败！")
                }
            }

        }

        return@Handler false
    }

    override fun setLayout(): Int = R.layout.activity_pay

    override fun initView() {
        showTitle(titleManager.defaultTitle("支付"))

        type = intent.getIntExtra("type",1)

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

                if (cbox_al.isChecked){
                    createAliOrder()
                }
            }
        }
    }

    override fun loadData() {
        if (type == 1){
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

                    data.content?.course?.let {
                        txt_order_number.text = ("订单编号：${it.id}")
                        txt_class_name.text = it.title
                        txt_order_time.text = (DateUtil.dateFormatYYMMdd(it.createTime) +"至"+DateUtil.dateFormatYYMMdd(it.expirationTime))
                        txt_money.text = ("实付：¥"+StringUtil.formatMoney((it.sumPrice?:"0").toDouble()))
                    }
                }

                override fun onFailed(code: String, message: String) {
                    hideLoading()
                }

            })
        }else{
            MyApplication.getApp().getData<ClassBean.Bean.Data>(Constant.COURSE,true).let { it ->
                it?.materialList?.let {
                    if (it.isNotEmpty()){
                        GlideUtil.loadPhoto(this@PayActivity,img_class_photo,it[0].url?:"")
                    }
                }
                txt_order_number.text = ("订单编号："+ it?.course?.id)
                txt_class_name.text = it?.course?.title
                txt_order_time.text = it?.course?.worktime
                txt_money.text = ("实付：¥"+StringUtil.formatMoney((it?.order?.paymentMoney?:"0").toDouble()))
            }

        }
    }

    companion object {
        fun start(context:Context,id:String){
            context.startActivity(Intent(context,PayActivity::class.java)
                    .putExtra("id",id)
                    .putExtra("type",1))
        }
        fun start(context:Context,data: ClassBean.Bean.Data){
            MyApplication.getApp().setData(Constant.COURSE,data)
            context.startActivity(Intent(context,PayActivity::class.java)
                    .putExtra("id",data.course.id)
                    .putExtra("type",2))
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


    private fun createAliOrder(){
        showLoading()
        val params = HashMap<String, String>()
        params["id"] = intent.getStringExtra("id") ?: ""
//        params["pintuan"] = ""
        ApiManager.post(composites,params,Constant.ORDER_CREATEORDERSTR,object :ApiManager.OnResult<BaseBean<String>>(){
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                if (data.content?.statu == "1"){
                    data.content?.data?.let {
                        aliPay(it)
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


    fun aliPay(authInfo:String){
        val authRunnable = Runnable{
            // 构造AuthTask 对象
            val payTask = PayTask(this)
            val result = payTask.payV2(authInfo, true)
            val message = Message()
            message.what = SDK_PAY_FLAG
            message.obj = result
            handler.sendMessage(message)
        }
        Thread(authRunnable).start()
    }
}