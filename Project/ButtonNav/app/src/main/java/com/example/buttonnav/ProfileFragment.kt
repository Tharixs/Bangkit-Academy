package com.example.buttonnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buttonnav.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
//make binding object

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.tvProfile.text = "This is Profile Fragment"
        return binding.root

    }

}