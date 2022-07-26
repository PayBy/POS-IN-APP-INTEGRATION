package com.payby.export.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.payby.export.demo.databinding.ActivityMainBinding
import com.payby.export.demo.util.openActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.saleBtn.setOnClickListener { openActivity<SaleActivity>() }
        binding.voidBtn.setOnClickListener { openActivity<VoidActivity>() }
        binding.inquireOrderBtn.setOnClickListener { openActivity<InquireActivity>() }
    }

}