package com.example.githubusers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubusers.databinding.ActivityDetileUserGitBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetileUserGitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetileUserGitBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetileUserGitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f

        mainViewModel.detile.observe(this) { detile ->
            setDetileData(detile)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.isError.observe(this) { message ->
            showError(message)
        }

        binding.viewPager.adapter = SectionAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setViewDetileData()
    }

    private fun setViewDetileData() {
        if (intent.getStringExtra(EXTRA_NAME) != null) {
            showLoading(true)
            mainViewModel.setDetileData(intent.getStringExtra(EXTRA_NAME).toString())
        } else {
            Toast.makeText(this, "Data tidak ada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDetileData(detile: DetileUserResponse?) {
        showLoading(false)
        if (detile != null) {
            supportActionBar?.title = detile.name
            binding.tvFollowers.text =
                resources.getString(R.string.followers, detile.followers.toString())
            binding.tvFollowing.text =
                resources.getString(R.string.following, detile.following.toString())
            binding.aboutName.text = detile.name
            binding.aboutLogin.text = detile.login
            Glide.with(this).load(detile.avatarUrl).into(binding.detileProfile)

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.title_followers, R.string.title_following
        )
        const val EXTRA_NAME = "extra_name"
    }
}