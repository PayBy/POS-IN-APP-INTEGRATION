package com.payby.export.demo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.payby.export.demo.App
import com.payby.export.demo.databinding.ActivityResultBinding
import com.payby.export.demo.ui.entity.NotificationRequest
import com.payby.export.demo.ui.entity.Response
import com.payby.export.demo.util.Logger
import com.payby.pos.export.api.PayByExportPaymentService
import org.json.JSONObject

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val responseString = intent.getStringExtra("extra_response") ?: ""

        // send notification response message
        sendNotificationResponse(responseString)

        val builder = StringBuilder()
        builder.append("Response ==> \n")
        try {
            val jsonObject = JSONObject(responseString)
            val keys = jsonObject.keys()
            for (key in keys) {
                val any = jsonObject[key]
                if (any != null && any is JSONObject) {
                    val subKeys = any.keys()
                    for (sunKey in subKeys) {
                        val subAny = any[sunKey]
                        builder.append("$sunKey:$subAny\n")
                    }
                } else {
                    builder.append("$key:$any\n")
                }
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
            builder.append(responseString)
        }
        binding.messageText.text = builder.toString()
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

    companion object {

        fun startAction(context: Context, response: String ? = null) {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("extra_response", response)
            context.startActivity(intent)
        }

    }

}