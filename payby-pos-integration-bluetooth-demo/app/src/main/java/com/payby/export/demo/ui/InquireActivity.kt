package com.payby.export.demo.ui

import android.os.Bundle
import com.google.gson.Gson
import com.payby.export.demo.App
import com.payby.export.demo.R
import com.payby.export.demo.databinding.ActivityInquireBinding
import com.payby.export.demo.ui.entity.Request
import com.payby.export.demo.util.Logger
import com.payby.pos.export.api.ExportResponseCallback
import com.payby.pos.export.api.PayByExportPaymentService

class InquireActivity : BaseActivity() {

    private lateinit var binding: ActivityInquireBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        var transactionType = "SALE"
        binding.transactionTypeRadio.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.transaction_type_sale -> transactionType = "SALE"
                R.id.transaction_type_void -> transactionType = "VOID"
            }
        }
        binding.okBtn.setOnClickListener {
            val request = Request()
            request.messageType = "Request"
            request.functionName = "GET_ORDER"
            request.requestTime = System.currentTimeMillis()
            request.messageId = System.currentTimeMillis().toString()
            request.misOrderNo = binding.orderNumberEdit.text.toString()
            request.transactionType = transactionType
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