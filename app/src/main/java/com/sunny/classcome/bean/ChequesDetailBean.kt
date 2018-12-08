package com.sunny.classcome.bean

/**
 * Desc 收款明细
 * Author JoannChen
 * Mail Q8622268@gmail.com
 * Date 2018/12/5 23:47
 */
data class ChequesDetailBean(
        var code: String,
        var msg: String,
        var content: Bean?) {

    data class Bean(
            var pageSize: String,
            var pageIndex: String,
            var totalPage: String,
            var curPageRecords: String,
            var totalRecords: String,
            var dataList: ArrayList<Data>?,
            var countStr: String,
            var countStrType: String,
            var remarks: String,
            var startIndex: String) {

        data class Data(
                var createTime: String, //创建时间
                var currentType: String, //支付方式： 1 支付宝 2微信
                var id: String,
                var orderNum: String, //订单号
                var payType: String, //支付类型： 1 进账 2支出 ,
                var paymentMoney: String, //支付金额
                var remarks: String, //备注
                var streamNo: String, //流水号
                var sumMoney: String,//支出统计（对应界面上余额）
                var type: String, //类型: 1：发布者付款， 2：平台付款 3代课收款 4，发布者退款 ,
                var userId: String //用户userId
        )
    }
}
