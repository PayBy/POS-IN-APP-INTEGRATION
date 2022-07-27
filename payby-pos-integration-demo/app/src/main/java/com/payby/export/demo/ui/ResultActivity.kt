package com.payby.export.demo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.payby.export.demo.databinding.ActivityResultBinding
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


    companion object {

        fun startAction(context: Context, response: String ? = null) {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("extra_response", response)
            context.startActivity(intent)
        }

    }

}