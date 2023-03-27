package com.example.githubusers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Callback

class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Items>>()
    val items: LiveData<List<Items>> = _items

    private val _detileRes = MutableLiveData<DetileUserResponse>()
    val detile: LiveData<DetileUserResponse> = _detileRes

    private val _followers = MutableLiveData<List<FollowResponseItem>>()
    val followers: LiveData<List<FollowResponseItem>> = _followers

    private val _following = MutableLiveData<List<FollowResponseItem>>()
    val following: LiveData<List<FollowResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getDataUserGit()
    }

    fun getDataUserGit(q: String = "Aril") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(q)
        client.enqueue(object : Callback<UsersGitResponse> {
            override fun onResponse(
                call: retrofit2.Call<UsersGitResponse>,
                response: retrofit2.Response<UsersGitResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _items.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<UsersGitResponse>, t: Throwable) {
                _isLoading.value = true
            }
        })
    }

    fun setDetileData(username: String = "Tharixs") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetileUserResponse> {
            override fun onResponse(
                call: retrofit2.Call<DetileUserResponse>,
                response: retrofit2.Response<DetileUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detileRes.value = response.body()
                } else {
                    _isLoading.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<DetileUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: retrofit2.Call<List<FollowResponseItem>>,
                response: retrofit2.Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    _isLoading.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = true
            }
        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: retrofit2.Call<List<FollowResponseItem>>,
                response: retrofit2.Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    _isLoading.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = true
            }
        })
    }
}