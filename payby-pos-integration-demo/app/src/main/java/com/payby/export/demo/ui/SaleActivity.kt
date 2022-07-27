package com.payby.export.demo.ui

import android.os.Bundle
import com.google.gson.Gson
import com.payby.export.demo.App
import com.payby.export.demo.R
import com.payby.export.demo.databinding.ActivitySaleBinding
import com.payby.export.demo.databinding.IncludePrintReceiptBinding
import com.payby.export.demo.ui.entity.NotificationRequest
import com.payby.export.demo.ui.entity.Request
import com.payby.export.demo.ui.entity.Response
import com.payby.export.demo.util.Logger
import com.payby.pos.export.api.ExportResponseCallback
import com.payby.pos.export.api.PayByExportPaymentService

class SaleActivity : BaseActivity() {

    private lateinit var binding: ActivitySaleBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val receiptBinding = IncludePrintReceiptBinding.bind(binding.root)
        initPrintReceiptType(receiptBinding.printReceiptRadio)
        var paymentMethod = ""
        binding.paymentMethodRadio.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.payment_method_pay_by -> paymentMethod = "PAYBY"
                R.id.payment_method_bom -> paymentMethod = "BOTIM"
                R.id.payment_method_card -> paymentMethod = "BANK_CARD"
            }
        }
        binding.okBtn.setOnClickListener {
            val request = Request()
            try {
                val amountString = binding.amountEdit.text.toString()
                request.totalAmount = amountString.toDouble()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
            request.messageType = "Request"
            request.functionName = "SALE"
            request.requestTime = System.currentTimeMillis()
            request.messageId = System.currentTimeMillis().toString()
            request.misOrderNo = binding.orderNumberEdit.text.toString()
            request.payMethod = paymentMethod
            request.subject = "a bottle of beer"
            request.printReceiptType = receiptType
            val jsonString = Gson().toJson(request)
            PayByExportPaymentService.getInstance().startTransaction(jsonString, responseCallback)
        }
    }

    private val responseCallback = object : ExportResponseCallback.Stub() {

        override fun onResponse(response: String ? ) {
            Logger.e(App.TAG, "response:$response")
            // send notification response message
            sendNotificationResponse(response ?: "")
            ResultActivity.startAction(baseContext, response)
            finish()
        }

    }

    private fun sendNotificationResponse(responseString: String) {
        try {
            val response = Gson().fromJson(responseString, Response::class.java)
            if (response.functionName == "NOTIFY_ORDER" && response.messageType == "Request") {
                Logger.e(App.TAG, "响应通知消息")
                val request = NotificationRequest()
                request.messageType = "Response"
                request.messageId = response.messageId
                request.functionName = "NOTIFY_ORDER"
                request.responseTime = System.currentTimeMillis()
                request.applyStatus = 0
                request.misOrderNo = response.misOrderNo
                val jsonString = Gson().toJson(request)
                PayByExportPaymentService.getInstance().startTransaction(jsonString, null)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}