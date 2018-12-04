package com.sunny.classcome.activity

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.sunny.classcome.R
import com.sunny.classcome.base.BaseActivity
import kotlinx.android.synthetic.main.activity_order_detail.*

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
    private var orderType = order_purchaser

    override fun setLayout(): Int = R.layout.activity_order_detail


    companion object {
        const val order_tobe_audited = 1    // 发布-待审核
        const val order_unaudited = 2       // 发布-审核未通过
        const val order_off_shelf = 3       // 发布-已下架

        const val order_class_pay = 21     // 代课-课程待支付
        const val order_class_ing = 22     // 代课-课程进行中
        const val order_class_finish = 23  // 代课-课程已结束

        const val order_pay_wait = 31       // 待支付
        const val order_paying = 32         // 支付中
        const val order_pay_finish = 33     // 评价售后

        const val order_field = 41          // 场地
        const val order_purchaser = 42      // 购买者
        const val order_winning_bid = 43    // 已中标
        const val order_settlement = 44     // 待结算
    }

    override fun initView() {
        showTitle(titleManager.defaultTitle(getString(R.string.order_detail)))

        when (orderType) {

            order_tobe_audited -> showOrderToBeAudited()
            order_unaudited -> showUnaudited()
            order_off_shelf -> showOffShelf()

            order_class_pay -> showClassPay()
            order_class_ing -> showClassIng()
            order_class_finish -> showClassFinish()

            order_pay_wait -> showPayWait()
            order_paying -> showPaying()
            order_pay_finish -> showPayFinish()

            order_field -> showField()
            order_purchaser -> showPurchaser()
            order_winning_bid -> showWinningBid()
            order_settlement -> showSettlement()

        }

        txt_date.text = "2018年12月4日 13：08"
        txt_class.text = "初中英语班课教研员-要求有专业教学资质"
        img_class.setImageResource(R.drawable.bg_default_photo)

    }

    private fun showField() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "您的信息正在发布中"
        showGrayBtn(txt_order_right, "取消发布")
    }

    private fun showPurchaser() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "系统默认将在核销完成后7天，对订单进行结算"
        txt_order_number.text = "订单编号：1234567890"
        txt_order_remark.text = "验证码：2344 3455 3545"
        showBlueBtn(txt_order_right, "核销")

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "支付金额"
        txt_money_count.text = "4000元"

        rl_contact.visibility = View.VISIBLE
        txt_contact_desc.text = "联系方式"
        txt_contact_phone.text = "13126596191"

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "购买者信息"
    }

    private fun showClassFinish() {
        txt_info.text = "课程已结束"
        showBlueBtn(txt_order_left, "再次发布")
        showBlueBtn(txt_order_right, "评价")
    }

    private fun showClassIng() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "系统默认将在课程结束后7天，对课程进行结算"
        txt_order_number.text = "订单编号：1234567890"
        txt_order_remark.text = "2018.12.05 至 2018.12.12"
        showGrayBtn(txt_order_left, "取消订单")
        showBlueBtn(txt_order_right, "结算")

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "实付款"
        txt_money_count.text = "4000元"

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "代课者信息"
    }

    private fun showClassPay() {
        txt_info.text = "课程已中标，付款后订单生效"
        txt_order_number.text = "订单编号：1234567890"
        txt_order_remark.text = "2018.12.05 至 2018.12.12"
        showGrayBtn(txt_order_left, "取消订单")
        showBlueBtn(txt_order_right, "去支付")

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "实付款"
        txt_money_count.text = "4000元"

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "代课者信息"
    }

    private fun showSettlement() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "系统默认将在课程结束后7天，对课程进行结算"

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "代课款"
        txt_money_count.text = "4000元"

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "发布者信息"
    }

    private fun showWinningBid() {
        txt_info.text = "您已中标，请按时完成代课"
        showGrayBtn(txt_order_right, "取消订单")

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "代课款"
        txt_money_count.text = "4000元"

        rl_info.visibility = View.VISIBLE
        txt_info_desc.text = "发布者信息"
    }

    private fun showPayFinish() {
        txt_info.text = "订单已完成"
        txt_order_number.text = "订单编号：1234567890"
        txt_order_remark.text = "验证码：2344 3455 3545"
        showBlueBtn(txt_order_right, "评价")
    }

    private fun showPaying() {
        txt_info.text = "订单进行中"
        txt_prompt.text = "您已付款成功"
        txt_order_number.text = "订单编号：1234567890"
        txt_order_remark.text = "验证码：2344 3455 3545"
        showGrayBtn(txt_order_right, "取消订单")
    }

    private fun showPayWait() {
        txt_info.text = "订单已生成，付款后订单生效"
        txt_order_number.text = "订单编号：1234567890"
        showGrayBtn(txt_order_left, "取消订单")
        showBlueBtn(txt_order_right, "去支付")

        rl_money.visibility = View.VISIBLE
        txt_money_desc.text = "实付款"
        txt_money_count.text = "4000元"

    }

    private fun showOffShelf() {
        txt_info.text = "已取消"
        txt_prompt.text = "您的信息已下架"
        showGrayBtn(txt_order_left, "删除")
        showBlueBtn(txt_order_right, "再次发布")
    }

    private fun showUnaudited() {
        txt_info.text = "审核未通过"
        txt_red_prompt.visibility = View.VISIBLE
    }

    private fun showOrderToBeAudited() {
        txt_info.text = "发布信息待审核"
        showGrayBtn(txt_order_right, "取消发布")
    }


    private fun showBlueBtn(txt: TextView, str: String) {
        txt.visibility = View.VISIBLE
        txt.text = str
        txt.setBackgroundResource(R.drawable.draw_bg_fillet_blue_border)
        txt.setTextColor(ContextCompat.getColor(this, R.color.color_nav_blue))
    }

    private fun showGrayBtn(txt: TextView, str: String) {
        txt.visibility = View.VISIBLE
        txt.text = str
        txt.setBackgroundResource(R.drawable.draw_bg_fillet_gray_border)
        txt.setTextColor(ContextCompat.getColor(this, R.color.color_gray_font))
    }

    override fun onClick(v: View?) {
    }
}