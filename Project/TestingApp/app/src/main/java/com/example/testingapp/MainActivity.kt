package com.example.testingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var btnSetValue : Button
    private lateinit var tvText : TextView
    private var names = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSetValue = findViewById(R.id.btnSetValue)
        tvText = findViewById(R.id.tv_text)

        names.add("Saipul")
        names.add("Ahmadan")
        names.add("Ciput")

        btnSetValue.setOnClickListener {
            Log.d("Mainctivity", names.toString())
            val name = StringBuilder()
            for (i in 0 .. 2){
                name.append(names[i]).append("\n")
            }
            tvText.text = name.toString()
        }
    }
}