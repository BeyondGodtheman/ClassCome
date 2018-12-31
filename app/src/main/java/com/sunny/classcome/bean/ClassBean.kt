package com.sunny.classcome.bean


data class ClassBean(

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
                var course: Course,
                var materialList: ArrayList<Material>?,
                var categoryList: ArrayList<Category>?,
                var user: User?,
                var order: Order,
                var userOrders: ArrayList<UserOrders>?,
                var isAppraise: String
        ) {
            data class Course(
                    var createTime: String,
                    var modifyTime: String,
                    var createUser: String,
                    var modifyUser: String,
                    var id: String,
                    var title: String,
                    var materialId: String,
                    var price: String?,
                    var courseNum: String,
                    var dicId: String,
                    var personType: String,
                    var classTime: String,
                    var classAddress: String,
                    var classDetailAdress: String,
                    var startTime: String?,
                    var endTime: String?,
                    var audit: String,
                    var category: String,
                    var description: String,
                    var userId: String,
                    var latitude: String?,
                    var longitude: String?,
                    var distance: String,
                    var winningBidder: String?,
                    var state: String,
                    var stateInfo: String,
                    var fatherCategoryName: String,
                    var pushPay: String,
                    var expirationTime: String,//截至日期
                    var cityId: String,
                    var countyId: String,
                    var townId: String,
                    var coursetype: String,
                    var onetime: String?,//单次使用时长
                    var onecost: String?, //培训 单次费用
                    var oneallcost: String?, //培训 单独购买价格
                    var captynum: String,//容纳人数
                    var workspace: String,//场地空间
                    var worktime: String,//营业时间
                    var commondevice: String?, //通用设施
                    var meetdevice: String?, //会议设施
                    var specialdevice: String?, //特殊设施
                    var assemcost: String? //拼团购买价格
            ) {
                var sumPrice: String? = null
                    get() {
                        if (coursetype == "4") {
                            return price
                        }
                        if (coursetype == "5") {
                            if (oneallcost == null) {
                                return onecost
                            }
                            return oneallcost
                        }
                        return field
                    }
            }

            data class Material(
                    var type: String?, //1头像 2轮播图 3素材图 4素材视频
                    var url: String?
            ) {
                var createTime: String? = null
                var modifyTime: String? = null
                var createUser: String? = null
                var modifyUser: String? = null
                var token: String? = null
                var md5: String? = null
                var userId: String? = null
                var id: String? = null
                var content: String? = null
            }

            data class Category(
                    var id: String,
                    var pId: String,
                    var name: String,
                    var sort: String

            )

            data class User(
                    var userName: String?,
                    var telephone: String?,
                    var userPic: String?)

            data class Order(
                    var id: String?,
                    var orderNum: String?,
                    var state: String?,
                    var modeOfPayment: String?,
                    var paymentMoney: String?,
                    var createTime: String?,
                    var createBy: String?,
                    var courseId: String?,
                    var modifyOrderTime: String?,
                    var effectTime: String?,
                    var pintuan:Int,
                    var pintuanId:String?,
                    var payFail: String?)

            data class UserOrders(
                    var id: String,
                    var orderNo: String,
                    var state: String,
                    var createTime: String,
                    var courseId: String,
                    var userId: String,
                    var payTime: String,
                    var money: String,
                    var payWay: String,
                    var pintuan: String,
                    var telephone: String,
                    var pintuanId: String)
        }

    }
}
