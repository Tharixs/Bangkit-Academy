package com.example.suitmedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmedia.databinding.ActivityMainBinding
import com.example.suitmedia.utils.EXTRA_NAME
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnCheck.setOnClickListener { onClickCheck() }
        binding.btnNext.setOnClickListener { onNextClick() }
    }

    private fun isPalindrome(text: String): Boolean {
        val normalisasi = text.replace("[^A-Za-z0-9]".toRegex(), "").lowercase()
        val reversed = normalisasi.reversed()
        return normalisasi == reversed
    }

    private fun onClickCheck() {
        val inputPalindrome = binding.edtPalindrome.text.toString()
        if (inputPalindrome.isNotEmpty()) {
            val result = isPalindrome(inputPalindrome)
            if (result) {
                Snackbar.make(binding.root, "Palindrome", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Not Palindrome", Snackbar.LENGTH_SHORT).show()
            }
        } else Snackbar.make(binding.root, "Please input text", Snackbar.LENGTH_SHORT).show()

    }

    private fun onNextClick() {
        if (binding.edtName.text.toString().isEmpty()) {
            Snackbar.make(binding.root, "Please input your name", Snackbar.LENGTH_SHORT).show()
            return
        } else {
            //intent to second activity
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(EXTRA_NAME, binding.edtName.text.toString())
            startActivity(intent)

            //clear input
            binding.edtName.text?.clear()
            binding.edtPalindrome.text?.clear()

            //jika ingin menutup activity ini dan ketik back pada screen berikutnya langsung keluar dari aplikasi
            //finish()

        }
    }

}