package com.payby.export.demo

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Message
import com.payby.export.demo.util.Logger
import com.payby.pos.export.api.PayByExportPaymentService

class App : Application() {

    companion object {
        const val TAG = "Export-Demo"
    }

    override fun onCreate() {
        super.onCreate()
        bindPaymentService()
        handler.sendEmptyMessageDelayed(0, 2 * 1000)
    }

    private fun bindPaymentService() {
        PayByExportPaymentService.getInstance().initialize(this, initializeCallback)
    }

    private val initializeCallback = object : PayByExportPaymentService.InitializeCallback {

        override fun onInitializeSuccess() {
            Logger.e(TAG, "onInitializeSuccess")
            handler.removeCallbacksAndMessages(0)
        }

        override fun onInitializeFailure() {
            Logger.e(TAG, "onInitializeFailure")
            handler.removeMessages(0)
            handler.sendEmptyMessageDelayed(0, 2 * 1000)
        }

    }

    private val handler = @SuppressLint("HandlerLeak") object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            removeMessages(0)
            if (PayByExportPaymentService.getInstance().isConnected) {
                return
            }
            bindPaymentService()
            sendEmptyMessageDelayed(0, 2 * 1000)
        }

    }

}