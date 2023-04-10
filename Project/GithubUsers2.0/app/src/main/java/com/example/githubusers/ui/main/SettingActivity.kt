package com.example.githubusers.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.data.local.pref.SettingPref
import com.example.githubusers.databinding.ActivitySettingBinding
import com.example.githubusers.helper.ViewModelFactory
import com.example.githubusers.ui.insert.MainViewModel

class SettingActivity :  AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Setting"
        supportActionBar?.elevation = 0f

        val pref = SettingPref.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, ViewModelFactory(application, pref))[MainViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) {isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }


        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
          mainViewModel.saveThemeSetting(isChecked)
        }
    }
}