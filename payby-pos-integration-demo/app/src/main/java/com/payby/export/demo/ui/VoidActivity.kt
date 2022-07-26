package com.payby.export.demo.ui

import android.os.Bundle
import com.payby.export.demo.BuildConfig
import com.payby.export.demo.databinding.ActivityVoidBinding
import com.payby.export.demo.databinding.IncludePrintReceiptBinding
import com.payby.export.demo.ui.entity.Request

/**
 * not support VOID transaction
 */
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
            var orderNumber = binding.orderNumberEdit.text.toString()
            val originalOrderNumber = binding.originalOrderNumberEdit.text.toString()
            if (orderNumber.trim().length < 8) {
                orderNumber = BuildConfig.APPLICATION_ID + System.currentTimeMillis()
            }
            request.requestTime = System.currentTimeMillis()
            request.messageId = System.currentTimeMillis().toString()
            request.misOrderNo = orderNumber
            request.originalMisOrderNo = originalOrderNumber
            request.functionName = "VOID"
            request.printReceiptType = receiptType
            startTransaction(request)
        }
    }

}