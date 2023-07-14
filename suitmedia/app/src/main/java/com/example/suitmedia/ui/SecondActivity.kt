package com.example.suitmedia.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmedia.R
import com.example.suitmedia.databinding.ActivitySecondBinding
import com.example.suitmedia.utils.EXTRA_NAME
import com.example.suitmedia.utils.EXTRA_NAME_USER


@Suppress("DEPRECATION")
class SecondActivity : AppCompatActivity() {

    private var name: String = DEFAULT_NAME
    private var nameUser: String = DEFAULT_NAME_USER

    private lateinit var binding: ActivitySecondBinding
    private lateinit var sharedPrefs: SharedPreferences

    @SuppressLint("UseCompatLoadingForDrawables", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            resources.getDrawable(R.drawable.bg_action_bar)
        )

        val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val title = actionBarLayout.findViewById<TextView>(R.id.title_action_bar)
        val backButton = actionBarLayout.findViewById<ImageButton>(R.id.backButton)

        title.text = getString(R.string.title_second_activity)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val layoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
        supportActionBar?.setCustomView(actionBarLayout, layoutParams)

        getData(savedInstanceState)
        binding.btnChoseUser.setOnClickListener { choseUser() }

    }

    private fun getData(savedInstanceState: Bundle?) {
        sharedPrefs = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        if (savedInstanceState != null) {
            name = savedInstanceState.getString(EXTRA_NAME, DEFAULT_NAME) ?: getSavedName()
            nameUser = savedInstanceState.getString(EXTRA_NAME_USER, DEFAULT_NAME_USER)
                ?: getSavedNameUser()
        } else {
            name = intent.getStringExtra(EXTRA_NAME) ?: getSavedName()
            nameUser = intent.getStringExtra(EXTRA_NAME_USER) ?: getSavedNameUser()
        }

        binding.tvUserName.text = name
        binding.tvDetailUserName.text = nameUser
    }

    private fun choseUser() {
        val intent = Intent(this, ThirdScreen::class.java)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NAME, name)
        outState.putString(EXTRA_NAME_USER, nameUser)
    }

    override fun onPause() {
        super.onPause()
        saveName(name)
        saveNameUser(nameUser)
    }

    private fun saveName(name: String) {
        sharedPrefs.edit().putString(KEY_NAME, name).apply()
    }

    private fun getSavedName(): String {
        return sharedPrefs.getString(KEY_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
    }

    private fun saveNameUser(nameUser: String) {
        sharedPrefs.edit().putString(KEY_NAME_USER, nameUser).apply()
    }

    private fun getSavedNameUser(): String {
        return sharedPrefs.getString(KEY_NAME_USER, DEFAULT_NAME_USER) ?: DEFAULT_NAME_USER
    }

    companion object {
        private const val DEFAULT_NAME = "Default Name"
        private const val DEFAULT_NAME_USER = "Selected User Name"
        private const val SHARED_PREF_NAME = "MySharedPrefs"
        private const val KEY_NAME = "name"
        private const val KEY_NAME_USER = "nameUser"
    }
}
