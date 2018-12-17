package com.sunny.classcome.bean

data class GeocodeBean(
        var status:String,
        var info:String,
        var infocode:String,
        var count:String,
        var geocodes:ArrayList<Geocodes>
){
    data class Geocodes(
            var formatted_address:String,
            var country:String,
            var province:String,
            var citycode:String,
            var city:String,
            var district:String,
            var adcode:String,
            var location:String,
            var level:String)
}