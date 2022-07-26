package com.payby.export.demo.ui

import android.os.Bundle
import com.payby.export.demo.BuildConfig
import com.payby.export.demo.R
import com.payby.export.demo.databinding.ActivitySaleBinding
import com.payby.export.demo.databinding.IncludePrintReceiptBinding
import com.payby.export.demo.ui.entity.Request

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
            var orderNumber = binding.orderNumberEdit.text.toString()
            if (orderNumber.trim().length < 8) {
                orderNumber = BuildConfig.APPLICATION_ID + System.currentTimeMillis()
            }
            request.messageType = "Request"
            request.functionName = "SALE"
            request.requestTime = System.currentTimeMillis()
            request.messageId = System.currentTimeMillis().toString()
            request.misOrderNo = orderNumber
            request.payMethod = paymentMethod
            request.subject = "a bottle of beer"
            request.printReceiptType = receiptType
            startTransaction(request)
        }
    }

}