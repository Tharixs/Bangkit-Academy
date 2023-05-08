package com.example.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.view.MainViewModelFactory
import com.example.storyapp.view.adapter.DetailAdapter
import com.example.storyapp.view.adapter.StoryAdapter
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.model.MainViewModel
import com.example.storyapp.view.model.TokenManager
import com.example.storyapp.view.network.DetailResponse
import com.example.storyapp.view.response.ListStoryItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tokenManager: TokenManager
    private lateinit var myViewModel: MainViewModel
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        get token from shared preferences
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE))

        if (tokenManager.getToken() == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val layoutManager = LinearLayoutManager(this)
            binding.rvStory.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvStory.addItemDecoration(itemDecoration)

            val factory = MainViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE), this)
            myViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
//            myViewModel.items.observe(this) { story ->
//                setItemsData(story)
//            }
             getData()
        }

        binding.addBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inalter = menuInflater
        inalter.inflate(R.menu.menu, menu)
        return true
    }

    private fun setItemsData(items: List<ListStoryItem>) {
        val adapter = StoryAdapter(items)
        binding.rvStory.adapter = adapter
        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(name: String, avatar: String, id: String) {
                val intent = Intent(this@MainActivity, DetileActivity::class.java)
                intent.putExtra(DetileActivity.EXTRA_ID, id)
                startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity).toBundle()
                )
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                tokenManager.clearToken()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_maps -> {
                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        val adapter = DetailAdapter()
        binding.rvStory.adapter = adapter
        myViewModel.detail.observe(this){
            adapter.submitData(lifecycle, it)
        }
    }

}