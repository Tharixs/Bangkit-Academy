package com.example.githubusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.databinding.FragmentHomeDetileBinding


class HomeDetileFragment : Fragment() {

    private lateinit var _binding: FragmentHomeDetileBinding
    private val binding get() = _binding

    companion object {
        const val EXTRA_POSITION = "extra_position"
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDetileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        viewModel.following.observe(viewLifecycleOwner) { following ->
            setFollowingsData(following)
        }
        viewModel.followers.observe(viewLifecycleOwner) { followers ->
            setFollowersData(followers)
        }

        val username = arguments?.getString(EXTRA_USERNAME)
        val position = arguments?.getInt(EXTRA_POSITION, 0)

        if (position == 1) {
            viewModel.getFollower(username.toString())
        } else {
            viewModel.getFollowing(username.toString())
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        return binding.root
    }

    private fun setFollowersData(followers: List<FollowResponseItem>) {
        val adapter = FollowAdapter(followers)
        binding.rvFollow.adapter = adapter

    }

    private fun setFollowingsData(following: List<FollowResponseItem>) {
        val adapter = FollowAdapter(following)
        binding.rvFollow.adapter = adapter
    }




}