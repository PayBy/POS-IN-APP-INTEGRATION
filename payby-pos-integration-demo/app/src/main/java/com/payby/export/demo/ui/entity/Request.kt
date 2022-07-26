package com.payby.export.demo.ui.entity

data class Request(
    var requestTime: Long = 0,
    var messageId: String ? = null,
    var messageType: String = "",
    var functionName: String = "",

    var misOrderNo: String ? = null,

    var transactionType: String ? = null,
    var originalMisOrderNo: String ? = null,

    var subject: String? = null,
    var payMethod: String? = null,
    var tipAmount: Double = 0.00,
    var totalAmount: Double = 0.00,
    var printReceiptType: Int = 3,
)