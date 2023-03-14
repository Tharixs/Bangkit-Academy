package com.example.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.navigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategory.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_categoryFragment)
        )
        binding.btnProfile.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_profileActivity)
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}