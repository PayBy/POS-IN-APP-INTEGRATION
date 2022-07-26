package com.payby.export.demo.ui

import android.os.Bundle
import com.payby.export.demo.BuildConfig
import com.payby.export.demo.databinding.ActivityInquireBinding
import com.payby.export.demo.ui.entity.Request

class InquireActivity : BaseActivity() {

    private lateinit var binding: ActivityInquireBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.okBtn.setOnClickListener {
            val request = Request()
            var orderNumber = binding.orderNumberEdit.text.toString()
            if (orderNumber.trim().length < 8) {
                orderNumber = BuildConfig.APPLICATION_ID + System.currentTimeMillis()
            }
            request.messageType = "Request"
            request.functionName = "GET_ORDER"
            request.requestTime = System.currentTimeMillis()
            request.messageId = System.currentTimeMillis().toString()
            request.misOrderNo = orderNumber
            request.transactionType = "SALE"
            startTransaction(request)
        }
    }

}