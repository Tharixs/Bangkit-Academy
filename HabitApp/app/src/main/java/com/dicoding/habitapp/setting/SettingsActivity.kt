package com.dicoding.habitapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.habitapp.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            val themePreference =
                findPreference<androidx.preference.ListPreference>(getString(R.string.pref_key_dark))
            themePreference?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    getString(R.string.pref_dark_on) -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    getString(R.string.pref_dark_off) -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    else -> false
                }
            }

            val notificationPreference =
                findPreference<androidx.preference.SwitchPreference>(getString(R.string.pref_key_notify))
            notificationPreference?.setOnPreferenceChangeListener { preference, newValue ->
                val sharedPreferences = preference.sharedPreferences
                when (newValue) {
                    true -> {
                        sharedPreferences?.edit()
                            ?.putBoolean(getString(R.string.pref_key_notify), true)?.apply()
                        true
                    }

                    else -> {
                        sharedPreferences?.edit()
                            ?.putBoolean(getString(R.string.pref_key_notify), false)?.apply()
                        true
                    }
                }
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}