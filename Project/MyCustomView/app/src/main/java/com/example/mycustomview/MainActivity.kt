package com.example.mycustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.mycustomview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMyBtnEnabled()

        binding.myEdText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyBtnEnabled()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.myButton.setOnClickListener{Toast.makeText(this@MainActivity, "Hello ${binding.myEdText.text}", Toast.LENGTH_SHORT).show()}
    }

    private fun setMyBtnEnabled(){
        val result = binding.myEdText.text
        binding.myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }
}