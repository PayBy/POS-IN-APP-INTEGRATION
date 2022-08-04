package com.payby.export.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.payby.ecr.kernel.ECRServiceKernel
import com.payby.export.demo.databinding.ActivityMainBinding
import com.payby.export.demo.util.openActivity

@Suppress("SimplifyBooleanWithConstants")
@SuppressLint("CheckResult")
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.saleBtn.setOnClickListener {
            val check = preCheck()
            if (check) {
                openActivity<SaleActivity>()
            }
        }
        binding.voidBtn.setOnClickListener {
            val check = preCheck()
            if (check) {
                openActivity<VoidActivity>()
            }
        }
        binding.inquireOrderBtn.setOnClickListener {
            val check = preCheck()
            if (check) {
                openActivity<InquireActivity>()
            }
        }
    }

    private fun preCheck(): Boolean {
        val ecrService = ECRServiceKernel.getInstance().ecrService
        if (ecrService == null) {
            showToast("Please connect to the ECR Service first")
            return false
        }
        val isConnected = try {
            ecrService.isConnected
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }
        if (isConnected == false) {
            showToast("Please use ECR Service for bluetooth connection")
            return false
        }
        return true
    }

}