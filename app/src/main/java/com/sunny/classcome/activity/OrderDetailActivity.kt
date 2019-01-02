package com.sunny.classcome.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.classcome.MyApplication
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import com.sunny.classcome.bean.BaseBean
import com.sunny.classcome.bean.BuyBean
import com.sunny.classcome.bean.ClassBean
import com.sunny.classcome.bean.OrderDetailBean
import com.sunny.classcome.http.ApiManager
import com.sunny.classcome.http.Constant
import com.sunny.classcome.utils.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Desc 订单详情页
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/4 22:09
 */
class OrderDetailActivity : BaseActivity() {

    /**
     * 订单类型
     */

    override fun setLayout(): Int = R.layout.activity_order_detail

    private var classBean: ClassBean.Bean.Data? = null
    private var buyBean: BuyBean? = null

    private var isAuthor = false

    companion object {
        fun start(context: Context, id: String, isAuthor: Boolean) {
            context.startActivity(Intent(context, OrderDetailActivity::class.java)
                    .putExtra("id", id)
                    .putExtra("isAuthor", isAuthor)
                    .putExtra("type", 1))
        }

        fun start(context: Context, classBean: ClassBean.Bean.Data, isAuthor: Boolean) {
            MyApplication.getApp().setData(Constant.COURSE, classBean)
            context.startActivity(Intent(context, OrderDetailActivity::class.java)
                    .putExtra("isAuthor", isAuthor)
                    .putExtra("type", 2))
        }

        //核销专用
        fun start(context: Context, classBean: ClassBean.Bean.Data, buyBean: BuyBean) {
            MyApplication.getApp().setData(Constant.BUYER, buyBean)
            MyApplication.getApp().setData(Constant.COURSE, classBean)
            context.startActivity(Intent(context, OrderDetailActivity::class.java)
                    .putExtra("isAuthor", true)
                    .putExtra("type", 3))
        }
    }

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.order_detail)))

        EventBus.getDefault().register(this)
        isAuthor = intent.getBooleanExtra("isAuthor", false)
        buyBean = MyApplication.getApp().getData<BuyBean>(Constant.BUYER, true)

        view_detail.setOnClickListener(this)
        rl_info.setOnClickListener(this)
    }

    private fun showAudited() {
        txt_info.text = "审核通过，未中标"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
        txt_order_remark.text = getTime()
        showGrayBtn(txt_order_left, "取消订单")
        txt_order_left.setOnClickListener {
            CancelPromptActivity.start(this, 1, classBean?.course?.id ?: "")
        }

        showBlueBtn(txt_order_mid, "邀请用户")
        txt_order_mid.setOnClickListener {
            InviteActivity.start(this, classBean?.course?.id ?: "")
        }
        showBlueBtn(txt_order_right, "应聘者")
        txt_order_right.setOnClickListener {
            ApplicantsActivity.start(this, classBean?.course?.id ?: "")
        }
    }

    private fun showField() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "您的信息正在发布中"
        showGrayBtn(txt_order_right, "取消发布")
        txt_order_right.setOnClickListener {
            CancelPromptActivity.start(this, 3, classBean?.course?.id ?: "")
        }
    }

    //核销操作
    private fun writeOff() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "系统默认将在核销完成后7天，对订单进行结算"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")

        showBlueBtn(txt_order_right, if (buyBean?.state == "3") "已核销" else "核销")
        txt_order_right.setOnClickListener {
            if (buyBean?.state != "3") {
                option()
            }
        }

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "支付金额"
        txt_money_count.text = ("￥${StringUtil.formatMoney((buyBean?.money ?: "0").toDouble())}")

        rl_contact.visibility = View.VISIBLE
        txt_contact_desc.text = "联系方式"
        txt_contact_phone.text = buyBean?.telephone ?: ""

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "购买者信息"
    }

    private fun option() {
        showLoading()
        val params = hashMapOf<String, String>()
        params["courseId"] = classBean?.course?.id ?: ""
        params["useUserId"] = buyBean?.userId ?: ""
        ApiManager.post(composites, params, Constant.ORDER_ACCOUNTSORDER, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1") {
                    buyBean?.state = "3"
                    txt_order_right.text = "已核销"
                }

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })

    }


    private fun showPurchaser() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "您的信息正在发布中"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")

        showGrayBtn(txt_order_mid, "取消发布")
        txt_order_mid.setOnClickListener {
            CancelPromptActivity.start(this, 3, classBean?.course?.id ?: "")
        }

        showBlueBtn(txt_order_right, "购买者")
        txt_order_right.setOnClickListener { _ ->
            classBean?.let {
                BuyActivity.start(this, classBean?.course?.id ?: "", it)
            }

        }

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "支付金额"
        txt_money_count.text = ("￥${classBean?.course?.sumPrice}")

//        rl_contact.visibility = View.VISIBLE
//        txt_contact_desc.text = "联系方式"
//        txt_contact_phone.text = classBean?.user?.telephone
//
//        rl_info.visibility = View.VISIBLE
//        txt_info_desc.text = "购买者信息"
    }

    private fun showClassFinish() {
        txt_info.text = "课程已结束"
        showBlueBtn(txt_order_left, "再次发布")
        txt_order_left.setOnClickListener {
            when (classBean?.course?.coursetype) {
                "4" -> startActivity(Intent(this, PublishFieldActivity::class.java))
                "5" -> startActivity(Intent(this, PublishTrainActivity::class.java))
                else -> {
                    PublishClassActivity.start(this, classBean?.course?.coursetype ?: "")
                }
            }
        }
//        showBlueBtn(txt_order_right, "评价")
    }

    private fun showClassIng() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "系统默认将在课程结束后7天，对课程进行结算"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
        txt_order_remark.text = getTime()
        showGrayBtn(txt_order_mid, "取消订单")
        txt_order_mid.setOnClickListener {
            CancelPromptActivity.start(this, 1, classBean?.course?.id ?: "")
        }

        showBlueBtn(txt_order_right, "结算")
        txt_order_right.setOnClickListener {
            account()
        }

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "实付款"
        txt_money_count.text = ("￥${classBean?.course?.sumPrice}")

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "代课者信息"
    }

    private fun showClassPay() {
        txt_info.text = "课程已中标，付款后订单生效"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
        txt_order_remark.text = getTime()
        showGrayBtn(txt_order_mid, "取消订单")
        txt_order_mid.setOnClickListener {
            CancelPromptActivity.start(this, 1, classBean?.course?.id ?: "")
        }

        showBlueBtn(txt_order_right, "去支付")
        txt_order_right.setOnClickListener {
            PayActivity.start(this, classBean?.course?.id ?: "")
        }

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "实付款"
        txt_money_count.text = ("￥${classBean?.course?.sumPrice}")

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "代课者信息"
    }

    private fun showSettlement() {

        txt_info.text = "订单进行中"
        txt_prompt.text = "系统默认将在课程结束后7天，对课程进行结算"

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "代课款"
        txt_money_count.text = ("￥${classBean?.course?.sumPrice}")

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "发布者信息"
    }

    private fun showWinningBid() {
        txt_info.text = "您已中标，请按时完成代课"
        showGrayBtn(txt_order_right, "取消订单")
        txt_order_right.setOnClickListener {
            CancelPromptActivity.start(this, 2, classBean?.course?.id ?: "")
        }

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "代课款"
        txt_money_count.text = ("￥${classBean?.course?.sumPrice}")

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "发布者信息"
    }

    private fun showPayFinish() {
        txt_info.text = "订单已完成"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
        txt_order_remark.text = ("验证码：${classBean?.order?.orderNum}")
//        showBlueBtn(txt_order_right, "评价")
    }

    private fun showPaying() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "您已付款成功"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
//        txt_order_remark.text = "验证码：2344 3455 3545"
        showGrayBtn(txt_order_right, "取消订单")
        txt_order_right.setOnClickListener {
            CancelPromptActivity.start(this, if (isAuthor) 3 else 4, classBean?.course?.id ?: "")
        }
    }

    //场地、培训待支付
    private fun showPayWait() {
        txt_info.text = "订单已生成，付款后订单生效"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
//        showGrayBtn(txt_order_mid, "取消订单")
//        txt_order_mid.setOnClickListener{
//            CancelPromptActivity.start(this,if (isAuthor) 1 else 2,classBean?.course?.id?:"")
//        }
        showBlueBtn(txt_order_right, "去支付")
        txt_order_right.setOnClickListener { _ ->
            classBean?.let {
                if (it.order.pintuanId == "0") {
                    PayActivity.start(this@OrderDetailActivity, it, "")
                } else {
                    PayActivity.start(this@OrderDetailActivity, it, it.order.pintuanId ?: "0")
                }
            }
        }
        rl_money.visibility = View.VISIBLE
        txt_date.text = DateUtil.dateFormatYYMMddHHssmm(classBean?.order?.createTime ?: "")
        txt_money_desc.text = "实付款"
        txt_money_count.text = ("￥${classBean?.order?.paymentMoney}")
    }

    private fun showOffShelf() {
        txt_info.text = "已取消"
        txt_prompt.text = "您的信息已下架"
//        showGrayBtn(txt_order_mid, "删除")
        showBlueBtn(txt_order_right, "再次发布")
        txt_order_right.setOnClickListener {
            when (classBean?.course?.coursetype) {
                "4" -> startActivity(Intent(this, PublishFieldActivity::class.java))
                "5" -> startActivity(Intent(this, PublishTrainActivity::class.java))
                else -> {
                    PublishClassActivity.start(this, classBean?.course?.coursetype ?: "")
                }
            }
        }
    }

    private fun showUnaudited() {
        txt_info.text = "审核未通过"
        txt_red_prompt.visibility = View.VISIBLE
    }

    private fun showOrderToBeAudited() {
        txt_info.text = "发布信息待审核"
        showGrayBtn(txt_order_right, "取消发布")
    }

    private fun cancleClass() {
        txt_info.text = "已取消"
        txt_prompt.text = "发布者取消"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
        txt_order_right.visibility = View.INVISIBLE
        txt_order_mid.visibility = View.INVISIBLE
        txt_order_left.visibility = View.INVISIBLE
    }


    private fun showOrderFinish() {
        txt_info.text = "订单已完成"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
        txt_order_remark.text = ("验证码：${classBean?.order?.orderNum}")
        showBlueBtn(txt_order_mid, "再次发布")
        txt_order_mid.setOnClickListener {

            when (classBean?.course?.coursetype) {
                "4" -> startActivity(Intent(this, PublishFieldActivity::class.java))
                "5" -> startActivity(Intent(this, PublishTrainActivity::class.java))
                else -> {
                    PublishClassActivity.start(this, classBean?.course?.coursetype ?: "")
                }

            }
        }
        showBlueBtn(txt_order_right, "应聘者")
        txt_order_right.setOnClickListener {
            ApplicantsActivity.start(this, classBean?.course?.id ?: "")
        }
    }


    private fun refund() {
        txt_info.text = "您已申请退款，请等待后台审核"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
    }

    private fun selfCancel() {
        txt_info.text = "订单已取消"
        txt_order_number.text = ("订单编号：${classBean?.course?.id}")
    }


    private fun getTime(): String {
        val startTime = classBean?.course?.startTime?.let {
            it.split(" ")[0]
        }
        val endTime = classBean?.course?.endTime?.let {
            it.split(" ")[0]
        }
        return "$startTime 至 $endTime"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.view_detail -> {
                PublishDetailsActivity.startPublishDetail(this, classBean?.course?.coursetype
                        ?: "", classBean?.course?.id ?: "")
            }
            R.id.rl_info -> {
                if (txt_info_desc.text.toString() == "发布者信息") {
                    MyProfileActivity.start(this, classBean?.course?.userId ?: "")
                } else if (txt_info_desc.text.toString() == "购买者信息") {
                    MyProfileActivity.start(this, buyBean?.userId ?: "")
                } else {
                    MyProfileActivity.start(this, classBean?.course?.winningBidder ?: "")
                }

            }
        }
    }


    override fun loadData() {
        //场地培训逻辑
        val type = intent.getIntExtra("type", 1)
        if (type == 2 || type == 3) {
            classBean = MyApplication.getApp().getData<ClassBean.Bean.Data>(Constant.COURSE, false)
            if (isAuthor) {
                when (classBean?.course?.state) {
                    "2" -> if (type == 2) showPurchaser() else writeOff()  //已支付
                    "3" -> showOffShelf() //已取消
                    "6" -> showPayFinish()
                }
            } else {
                when (classBean?.order?.state) {
                    "-1" -> selfCancel() //已取消
                    "1" -> {
                        if (classBean?.course?.state == "3"){
                            selfCancel()
                        }else{
                            showPayWait()
                        }
                    }  //待支付
                    "2" -> {
                        if (classBean?.course?.state != "3") {
                            showPaying()
                        } //进行中
                        else {
                            selfCancel()
                        }
                    }
                    "3" -> {
                        //参与培训和场已结算 发布为
                        if (classBean?.course?.state == "3") {
                            cancleClass() //进行中 //用户取消发布
                        } else {
                            if (classBean?.course?.coursetype == "4" || classBean?.course?.coursetype == "5") {
                                showPayFinish()
                            }
                        }
                    }
                    "4" -> {
                        if (classBean?.course?.coursetype == "4" || classBean?.course?.coursetype == "5") {
                            selfCancel()
                        }
                    }
                    "5" -> showPayFinish() //完成
                }

            }


            txt_date.text = DateUtil.dateFormatYYMMddHHssmm(classBean?.course?.createTime ?: "")
            txt_class.text = classBean?.course?.title
            classBean?.materialList?.let {
                if (it.isNotEmpty()) {
                    GlideUtil.loadPhoto(this@OrderDetailActivity, img_class, it[0].url ?: "")
                }
            }
            return
        }

        showLoading()
        val params = HashMap<String, String>()
        params["id"] = intent.getStringExtra("id") ?: ""
        ApiManager.post(composites, params, Constant.ORDER_GETORDERDETAILNEW,
                object : ApiManager.OnResult<OrderDetailBean>() {
                    override fun onSuccess(data: OrderDetailBean) {
                        hideLoading()
                        classBean = data.content
                        if (isAuthor) {
                            when (data.content?.order?.state) {
                                "2" -> showAudited() //订单邀请应聘
                                "3" -> showOffShelf() //已取消发布
                                "4" -> showClassPay()
                                "5" -> {
                                    if (data.content?.course?.state == "5") {
                                        showClassIng()
                                    }
                                    if (data.content?.course?.state == "6") {
                                        showClassFinish()
                                    }
                                }
                                "6" -> {
                                    showOrderFinish()
                                }
                                "8" -> {
                                    refund()
                                }
                            }
                        } else {
                            when (data.content?.order?.state) {
                                "1" -> showOffShelf()
                                "2" -> showWinningBid() //中标
                                "3" -> {
                                    if (data.content?.course?.state == "3") {
                                        cancleClass()
                                    } else {
                                        showSettlement()
                                    }
                                }
                                "4" -> {
                                    if (data.content?.course?.state == "4") {
                                        showWinningBid()
                                    }

                                }
                                "5" -> showSettlement() //待结算
                            }
                        }

                        txt_date.text = DateUtil.dateFormatYYMMddHHssmm(classBean?.course?.createTime
                                ?: "")
                        txt_class.text = classBean?.course?.title
                        classBean?.materialList?.let {
                            if (it.isNotEmpty()) {
                                GlideUtil.loadPhoto(this@OrderDetailActivity, img_class, it[0].url
                                        ?: "")
                            }
                        }

                    }

                    override fun onFailed(code: String, message: String) {
                        hideLoading()
                    }

                })

    }

    override fun close() {
        EventBus.getDefault().unregister(this)
        MyApplication.getApp().removeData(Constant.COURSE)
    }

    //结算
    private fun account() {
        val params = hashMapOf<String, String>()
        params["courseId"] = classBean?.course?.id ?: ""
        params["useUserId"] = classBean?.course?.winningBidder ?: ""
        ApiManager.post(composites, params, Constant.ORDER_ACCOUNTSORDER, object : ApiManager.OnResult<BaseBean<String>>() {
            override fun onSuccess(data: BaseBean<String>) {
                hideLoading()
                ToastUtil.show(data.content?.info)
                if (data.content?.statu == "1") {
                    loadData()
                }

            }

            override fun onFailed(code: String, message: String) {
                hideLoading()
            }

        })
    }

    //更新列表数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUpdateEvent(posted: Posted) {
        finish()
    }
}