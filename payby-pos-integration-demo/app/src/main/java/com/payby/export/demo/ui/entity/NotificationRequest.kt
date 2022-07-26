package com.payby.export.demo.ui.entity

data class NotificationRequest(
    var applyStatus: Int = -1,
    var responseTime: Long = 0,
    var messageId: String ? = null,
    var messageType: String = "",
    var functionName: String = "",
    var misOrderNo: String ? = null,
)