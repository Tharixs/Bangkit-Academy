package com.example.mafilmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import com.bumptech.glide.Glide
import com.example.mafilmapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "About Page"
        val aboutProfile =  resources.getString(R.string.about_photo)
        Glide.with(binding.circleImageView).load(aboutProfile).into(binding.circleImageView)

    }
}