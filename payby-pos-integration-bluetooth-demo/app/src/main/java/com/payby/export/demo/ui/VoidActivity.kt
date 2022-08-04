package com.payby.export.demo.ui

import android.os.Bundle
import com.google.gson.Gson
import com.payby.ecr.kernel.ECRServiceKernel
import com.payby.export.demo.App
import com.payby.export.demo.databinding.ActivityVoidBinding
import com.payby.export.demo.databinding.IncludePrintReceiptBinding
import com.payby.export.demo.ui.entity.Request
import com.payby.export.demo.util.Logger
import com.pos.connection.bridge.ECRListener

@Suppress("IfThenToSafeAccess")
class VoidActivity : BaseActivity() {

    private lateinit var binding: ActivityVoidBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        register()
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
            send(jsonString)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregister()
    }

    private fun send(string: String) {
        try {
            val bytes = string.toByteArray(Charsets.UTF_8)
            val ecrService = ECRServiceKernel.getInstance().ecrService
            if (ecrService != null) {
                ecrService.send(bytes, null)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun register() {
        try {
            val ecrService = ECRServiceKernel.getInstance().ecrService
            if (ecrService != null) {
                ecrService.register(listener)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun unregister() {
        try {
            val ecrService = ECRServiceKernel.getInstance().ecrService
            if (ecrService != null) {
                ecrService.unregister(listener)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private val listener = object : ECRListener.Stub() {

        override fun onReceive(bytes: ByteArray ? ) {
            try {
                if (bytes != null) {
                    val response = String(bytes, Charsets.UTF_8)
                    Logger.e(App.TAG, "response:$response")
                    ResultActivity.startAction(baseContext, response)
                    finish()
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

    }

}