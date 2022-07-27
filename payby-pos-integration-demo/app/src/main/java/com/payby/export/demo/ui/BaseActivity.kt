package com.payby.export.demo.ui

import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.payby.export.demo.R

open class BaseActivity : AppCompatActivity() {

    var receiptType = 3

    fun initPrintReceiptType(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.print_receipt_none -> receiptType = 0
                R.id.print_receipt_customer -> receiptType = 1
                R.id.print_receipt_merchant -> receiptType = 2
                R.id.print_receipt_both -> receiptType = 3
            }
        }
    }

}