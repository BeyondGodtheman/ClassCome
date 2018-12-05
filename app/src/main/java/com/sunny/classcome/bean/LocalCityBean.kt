package com.sunny.classcome.bean

/**
 * Desc
 * Author Justin
 * Mail zhangye98@foxmail.com
 * Date 2018/12/1 23:42
 */
data class LocalCityBean(
        var msg: String,
        var code: String,
        var content: ArrayList<City>) {


    data class City(
            var cityVoId: String,
            var cityVoName: String,
            var countyList: ArrayList<County>
    ) {

        data class County(
                var countyId: String,
                var countyName: String,
                var townlist: ArrayList<Town>
        ) {
            data class Town(
                    var townId: String,
                    var townName: String)
        }
    }
}