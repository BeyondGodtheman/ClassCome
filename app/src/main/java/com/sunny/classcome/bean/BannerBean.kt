package com.sunny.classcome.bean

data class BannerBean(
        var code: String,
        var msg: String,
        var content: ArrayList<Bean>
) {
    data class Bean(
            var createTime: String,
            var modifyTime: String,
            var createUser: String,
            var modifyUser: String,
            var token: String,
            var url: String,
            var type: String,
            var md5: String,
            var userId: String,
            var id: String,
            var content: String
    )
}