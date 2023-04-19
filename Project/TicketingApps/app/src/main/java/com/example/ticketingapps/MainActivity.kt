package com.example.ticketingapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.ticketingapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportRequestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        supportActionBar?.hide()

        setContentView(binding.root)

        binding.bookingBtn.setOnClickListener {
            binding.seatsView.seat?.let {
                Toast.makeText(this, "Seat ${it.name} is booked", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_SHORT).show()
            }
        }
    }
}