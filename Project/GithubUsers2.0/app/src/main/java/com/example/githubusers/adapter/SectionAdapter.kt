package com.example.githubusers.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusers.ui.main.DetileUserGitActivity
import com.example.githubusers.ui.main.HomeDetileFragment

class SectionAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private var username = activity.intent.getStringExtra(DetileUserGitActivity.EXTRA_NAME)
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = HomeDetileFragment()
        fragment.arguments = Bundle().apply {
            putInt(HomeDetileFragment.EXTRA_POSITION, position + 1)
            putString(HomeDetileFragment.EXTRA_USERNAME, username)
        }
        return fragment
    }
}