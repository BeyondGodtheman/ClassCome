package com.sunny.classcome.bean

data class ScoreRuleBean(
        var code: String,
        var msg: String,
        var content: Bean?
) {
    data class Bean(
            var novice:ArrayList<Data>?,
            var daily:ArrayList<Data>?){

        data class Data(
                var code:String,
                var dict_value:String,
                var dict_label:String,
                var dict_sort:String,
                var remark:String,
                var payment:String)

    }
}