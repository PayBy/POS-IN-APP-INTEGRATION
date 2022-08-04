package com.payby.export.demo

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Message
import com.payby.ecr.kernel.ECRServiceKernel
import com.payby.export.demo.util.Logger

class App : Application() {

    companion object {
        const val TAG = "Export-Demo"
    }

    override fun onCreate() {
        super.onCreate()
        bindECRService()
        handler.sendEmptyMessageDelayed(0, 2 * 1000)
    }

    private fun bindECRService() {
        ECRServiceKernel.getInstance().bindService(this, connectionCallback)
    }

    private val connectionCallback = object : ECRServiceKernel.ConnectionCallback {

        override fun onServiceConnected() {
            Logger.e(TAG, "onServiceConnected")
            handler.removeCallbacksAndMessages(0)
        }

        override fun onServiceDisconnected() {
             Logger.e(TAG, "onServiceDisconnected")
            handler.removeMessages(0)
            handler.sendEmptyMessageDelayed(0, 2 * 1000)
        }

    }

    private val handler = @SuppressLint("HandlerLeak") object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            removeMessages(0)
            if (ECRServiceKernel.getInstance().ecrService != null) {
                return
            }
            bindECRService()
            sendEmptyMessageDelayed(0, 2 * 1000)
        }

    }

}