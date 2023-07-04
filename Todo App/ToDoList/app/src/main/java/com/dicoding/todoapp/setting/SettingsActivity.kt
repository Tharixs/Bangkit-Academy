package com.dicoding.todoapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dicoding.todoapp.R
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit

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

            val prefNotification =
                findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                val channelName = getString(R.string.notify_channel_name)
                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName
                val sharedPreferences = preference.sharedPreferences
                if (newValue == true) {
                    scheduleReminder(channelName)
                    sharedPreferences?.edit()?.putBoolean(
                        getString(R.string.pref_key_notify),
                        true
                    )?.apply()

                } else {
                    cancelReminder()
                    sharedPreferences?.edit()?.putBoolean(
                        getString(R.string.pref_key_notify),
                        false
                    )?.apply()
                }

                true
            }
        }

        private fun scheduleReminder(channelName: String) {
            val data = Data.Builder()
                .putString(NOTIFICATION_CHANNEL_ID, channelName)
                .build()
            val reminderRequest =
                PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
                    .setInputData(data)
                    .build()
            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                "DailyReminder",
                ExistingPeriodicWorkPolicy.KEEP,
                reminderRequest
            )

        }

        private fun cancelReminder() {
            WorkManager.getInstance(requireContext()).cancelUniqueWork("DailyReminder")
        }


        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}