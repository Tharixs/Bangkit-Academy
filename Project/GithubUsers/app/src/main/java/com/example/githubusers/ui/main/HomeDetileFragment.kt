package com.example.githubusers.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.adapter.FollowAdapter
import com.example.githubusers.databinding.FragmentHomeDetileBinding
import com.example.githubusers.ui.insert.MainViewModel
import com.example.githubusers.data.remote.response.FollowResponseItem


class HomeDetileFragment : Fragment() {

    private var _binding: FragmentHomeDetileBinding? = null
    private val binding get() = _binding!!

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
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        viewModel.following.observe(viewLifecycleOwner) { following ->
            setFollowingsData(following)
        }
        viewModel.followers.observe(viewLifecycleOwner) { followers ->
            setFollowersData(followers)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.isError.observe(viewLifecycleOwner) { message ->
            showError(message)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        setDataToView()
        return binding.root
    }

    private fun setFollowersData(followers: List<FollowResponseItem>) {
        val adapter = FollowAdapter(followers)
        binding.rvFollow.adapter = adapter
    }

    private fun setDataToView() {
        val username = arguments?.getString(EXTRA_USERNAME)
        val position = arguments?.getInt(EXTRA_POSITION, 0)

        if (position == 1) {
            viewModel.getFollower(username.toString())
        } else {
            viewModel.getFollowing(username.toString())
        }
    }

    private fun setFollowingsData(following: List<FollowResponseItem>) {
        val adapter = FollowAdapter(following)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}