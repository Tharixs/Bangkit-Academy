package com.example.githubusers.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.adapter.FavoriteAdapter
import com.example.githubusers.data.local.entity.Favorite
import com.example.githubusers.databinding.ActivityFavoritBinding
import com.example.githubusers.helper.ViewModelFactory
import com.example.githubusers.ui.insert.FavoriteAddUpdateViewModel
import com.example.githubusers.ui.insert.MainViewModel

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory(
            application,
            null
        )
    }

    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAddUpdateViewModel = obtainViewModel(this@FavoritActivity)

        supportActionBar?.title = "Favorite"
        supportActionBar?.elevation = 0f

        mainViewModel.favorite.observe(this) { items ->
            setItemsData(items)
        }

        favoriteAddUpdateViewModel.getAllFavorite().observe(this) {
            if (it != null) {
                setItemsData(it)
            } else {
                setItemsData(listOf())
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

    }

    private fun obtainViewModel(
        activity: AppCompatActivity,
    ): FavoriteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, null)
        return ViewModelProvider(activity, factory)[FavoriteAddUpdateViewModel::class.java]
    }

    private fun setItemsData(items: List<Favorite>) {
        val adapter = FavoriteAdapter(items)
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String, avatar: String?) {
                val intent = Intent(this@FavoritActivity, DetileUserGitActivity::class.java)
                intent.putExtra(DetileUserGitActivity.EXTRA_NAME, data)
                intent.putExtra(DetileUserGitActivity.EXTRA_AVATAR, avatar)
                startActivity(intent)
            }
        })
    }

}