package com.example.storyapp.view.main

import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityDetileBinding
import com.example.storyapp.view.MainViewModelFactory
import com.example.storyapp.view.model.MainViewModel
import com.example.storyapp.view.response.DetileStoryResponse
import com.example.storyapp.view.response.Story

class DetileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetileBinding
    private lateinit var myViewModel: MainViewModel

    companion object {
        const val EXTRA_ID = "ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)

        val factory = MainViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE), this)
        myViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        myViewModel.getDetileWithid(id.toString())
        myViewModel.detile.observe(this) { detile ->
            setDetileView(detile)
        }
    }

    private fun setDetileView(detile: Story) {
            binding.tvNameUser.text = detile.name
            binding.tvDeskripsi.text = detile.description
            Glide.with(this)
                .load(detile.photoUrl)
                .into(binding.ivStory)
    }


}
