package com.payby.export.demo.ui

import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.payby.export.demo.App
import com.payby.export.demo.BuildConfig
import com.payby.export.demo.R
import com.payby.export.demo.ui.entity.Request
import com.payby.export.demo.util.Logger
import com.payby.pos.export.api.ExportResponseCallback
import com.payby.pos.export.api.PayByExportPaymentService

open class BaseActivity : AppCompatActivity() {

    var receiptType = 3

    fun initPrintReceiptType(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.print_receipt_none -> receiptType = 0
                R.id.print_receipt_customer -> receiptType = 1
                R.id.print_receipt_merchant -> receiptType = 2
                R.id.print_receipt_both -> receiptType = 3
            }
        }
    }

    fun startTransaction(request: Request) {
        val jsonString = Gson().toJson(request)
        PayByExportPaymentService.getInstance().startTransaction(jsonString, responseCallback)
    }

    /**
     * for test
     */
    fun demo() {
        val request = Request()
        request.messageId = System.currentTimeMillis().toString()
        request.messageType = "Request"
        request.requestTime = System.currentTimeMillis()
        request.functionName = "SALE"
        request.misOrderNo = BuildConfig.APPLICATION_ID + "_" + System.currentTimeMillis().toString()
        request.totalAmount = 12.12
        request.payMethod = "BANK_CARD"
        request.subject = "a bottle of beer"
        request.printReceiptType = 1
        val jsonString = Gson().toJson(request)
        PayByExportPaymentService.getInstance().startTransaction(jsonString, responseCallback)
    }

    private val responseCallback = object : ExportResponseCallback.Stub() {

        override fun onResponse(response: String ? ) {
            Logger.e(App.TAG, "response:$response")
            ResultActivity.startAction(baseContext, response)
            finish()
        }

    }

}