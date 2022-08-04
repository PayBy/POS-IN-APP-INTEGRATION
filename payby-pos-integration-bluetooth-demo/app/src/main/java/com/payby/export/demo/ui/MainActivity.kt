package com.payby.export.demo.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.payby.export.demo.App
import com.payby.export.demo.R
import com.payby.export.demo.databinding.ActivityMainBinding
import com.payby.export.demo.util.Logger
import com.payby.export.demo.util.openActivity

@SuppressLint("CheckResult")
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private var bluetoothAddress = ""
    private val bluetoothList = HashMap<String, String>()

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
        binding.bluetoothModeBtn.setOnClickListener {
            selectBluetooth()
        }
    }

    private fun selectBluetooth() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled) {
                bluetoothList.clear()
                val bondedDevices = bluetoothAdapter.bondedDevices
                if (bondedDevices != null && bondedDevices.size > 0) {
                    for (bluetoothDevice in bondedDevices) {
                        val name = bluetoothDevice.name
                        val address = bluetoothDevice.address
                        Logger.e(App.TAG, "name: $name")
                        Logger.e(App.TAG, "address: $address")
                        if (address == "00:11:22:33:44:55") {
                            // this is default printer bluetooth
                            continue
                        }
                        bluetoothList["$name - $address"] = address
                    }
                }
                if (bluetoothList.size > 0) {
                    showBluetoothDialog()
                } else {
                    showToast(R.string.error_bluetooth_not_find_bonded)
                }
            } else {
                showToast(R.string.error_bluetooth_disable)
            }
        } else {
            showToast(R.string.error_bluetooth_not_support)
        }
    }

    private fun showBluetoothDialog() {
        val arrays = bluetoothList.keys.toList()
        MaterialDialog(this).show {
            setCancelable(false)
            message(R.string.dialog_select_bluetooth)
            setCanceledOnTouchOutside(false)
            listItems(items = arrays) { _, index, text ->
                Logger.e(App.TAG, "index: $index text: $text")
                bluetoothAddress = bluetoothList[text] ?: ""
            }
            positiveButton(R.string.ok)
            lifecycleOwner(this@MainActivity)
        }
    }

}