package com.payby.export.demo.ui

import android.os.Bundle
import com.google.gson.Gson
import com.payby.export.demo.App
import com.payby.export.demo.databinding.ActivityVoidBinding
import com.payby.export.demo.databinding.IncludePrintReceiptBinding
import com.payby.export.demo.ui.entity.Request
import com.payby.export.demo.util.Logger
import com.payby.pos.export.api.ExportResponseCallback
import com.payby.pos.export.api.PayByExportPaymentService

class VoidActivity : BaseActivity() {

    private lateinit var binding: ActivityVoidBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val receiptBinding = IncludePrintReceiptBinding.bind(binding.root)
        initPrintReceiptType(receiptBinding.printReceiptRadio)
        binding.okBtn.setOnClickListener {
            val request = Request()
            request.messageType = "Request"
            request.functionName = "VOID"
            request.requestTime = System.currentTimeMillis()
            request.messageId = System.currentTimeMillis().toString()
            request.misOrderNo = binding.orderNumberEdit.text.toString()
            request.originalMisOrderNo = binding.originalOrderNumberEdit.text.toString()
            request.printReceiptType = receiptType
            val jsonString = Gson().toJson(request)
            PayByExportPaymentService.getInstance().startTransaction(jsonString, responseCallback)
        }
    }

    private val responseCallback = object : ExportResponseCallback.Stub() {

        override fun onResponse(response: String ? ) {
            Logger.e(App.TAG, "response:$response")
            ResultActivity.startAction(baseContext, response)
            finish()
        }

    }

}