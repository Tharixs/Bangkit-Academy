package com.example.mafilmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mafilmapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Hero>("DATA")
        supportActionBar?.title = data?.name.toString()
        Glide.with(binding.imgDetileHero).load(data?.photo).into(binding.imgDetileHero)
        binding.tvStar.text = data?.star
        binding.tvDetileTime.text = data?.time
        binding.tvDetileDesk.text = data?.desk
        binding.tvDetileSinop.text = data?.sinopsis
        binding.detileTanggalRilis.text = data?.rilis
        binding.detileSutradara.text = data?.sutradara
    }
}