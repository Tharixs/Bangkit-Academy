package com.example.githubusers.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.adapter.SectionAdapter
import com.example.githubusers.data.local.entity.Favorite
import com.example.githubusers.data.remote.response.DetileUserResponse
import com.example.githubusers.databinding.ActivityDetileUserGitBinding
import com.example.githubusers.helper.ViewModelFactory
import com.example.githubusers.ui.insert.FavoriteAddUpdateViewModel
import com.example.githubusers.ui.insert.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetileUserGitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetileUserGitBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(
            application, null
        )
    }

    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetileUserGitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAddUpdateViewModel = obtainViewModel(this@DetileUserGitActivity)

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


//        implement fun getFavoriteByUsername form view model in here
        favoriteAddUpdateViewModel.getFavoriteByUsername(
            intent.getStringExtra(EXTRA_NAME).toString()
        )
            .observe(this) {
                if (it != null) {
                    binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                    ISCLICED = true
                } else {
                    binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                    ISCLICED = false
                }
            }

        binding.viewPager.adapter = SectionAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setViewDetileData()

        binding.fab.setOnClickListener {
            val favorite = Favorite()
            if (ISCLICED) {
                favorite.username = intent.getStringExtra(EXTRA_NAME).toString()
                favorite.avatar = intent.getStringExtra(EXTRA_AVATAR).toString()
                favoriteAddUpdateViewModel.delete(favorite)
                Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
            } else {
                favorite.username = intent.getStringExtra(EXTRA_NAME).toString()
                favorite.avatar = intent.getStringExtra(EXTRA_AVATAR).toString()
                favoriteAddUpdateViewModel.insert(favorite)
                Toast.makeText(this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, null)
        return ViewModelProvider(activity, factory)[FavoriteAddUpdateViewModel::class.java]
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
        var EXTRA_AVATAR: String? = null
        var ISCLICED: Boolean = false
    }


}