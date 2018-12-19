package com.sunny.classcome.bean

data class JourneyBean(
        var code: String,
        var msg: String,
        var content: ArrayList<Bean>?
) {
    data class Bean(
            var date: String,
            var dateStr: String,
            var week: String,
            var journeyInfo: ArrayList<Entity>?
    ) {
        data class Entity(
                var time: String,
                var url: String,
                var title: String,
                var courseId: String)
    }
}