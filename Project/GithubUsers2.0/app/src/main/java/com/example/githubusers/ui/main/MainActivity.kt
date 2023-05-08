package com.example.githubusers.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.adapter.UsersAdapter
import com.example.githubusers.data.local.pref.SettingPref
import com.example.githubusers.data.remote.response.Items
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.helper.ViewModelFactory
import com.example.githubusers.ui.insert.MainViewModel

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory(application, null) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Github Users"

        val pref = SettingPref.getInstance(dataStore)
        val mainVModel =
            ViewModelProvider(this, ViewModelFactory(application, pref))[MainViewModel::class.java]
        mainVModel.getThemeSettings().observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.items.observe(this) { items ->
            setItemsData(items)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isError.observe(this) { message ->
            showError(message)
        }
        val layoutManager = LinearLayoutManager(this)
        binding?.recyclerView?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search Github Users"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    showLoading(false)
                    mainViewModel.getDataUserGit(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.action_favorite -> {
                val i = Intent(this, FavoritActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun setItemsData(items: List<Items>) {
        val adapter = UsersAdapter(items)
        binding?.recyclerView?.adapter = adapter
        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String, avatar: String) {
                val intent = Intent(this@MainActivity, DetileUserGitActivity::class.java)
                intent.putExtra(DetileUserGitActivity.EXTRA_NAME, data)
                intent.putExtra(DetileUserGitActivity.EXTRA_AVATAR, avatar)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}