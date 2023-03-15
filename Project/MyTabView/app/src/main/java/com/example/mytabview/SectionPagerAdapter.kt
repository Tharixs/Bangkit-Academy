package com.example.mytabview

import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var appName : String = ""

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = HomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
            putString(HomeFragment.ARG_APP_NAME, appName)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
    }

}