package com.example.mydeskproductviewandviewgroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var btnNext : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "My Pay"

        btnNext =  findViewById(R.id.nextBtn)

        btnNext.setOnClickListener {
            startActivity(Intent(this@MainActivity,WithConstrainLay::class.java))
        }

    }
}