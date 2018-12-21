package com.sunny.classcome.bean

data class ClassTypeBean(
        var id:String,
        var pId:String,
        var name:String,
        var sort:String,
        var subCategoryList:ArrayList<SubCategory>){

    data class SubCategory(
            var id:String,
            var pId:String,
            var name:String,
            var sort:String
    )
}