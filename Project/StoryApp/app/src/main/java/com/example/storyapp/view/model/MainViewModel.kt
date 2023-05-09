package com.example.storyapp.view.model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.view.data.DetailRepository
import com.example.storyapp.view.network.DetailResponse
import com.example.storyapp.view.response.DetileStoryResponse
import com.example.storyapp.view.response.ListStoryItem
import com.example.storyapp.view.response.StoriesResponse
import com.example.storyapp.view.response.Story
import com.example.storyapp.view.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val sharedPreferences: SharedPreferences,
    detailRepository: DetailRepository
) : ViewModel() {

    //    token
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val tokenManager: TokenManager = TokenManager.getInstance(sharedPreferences)

    private val _item = MutableLiveData<List<ListStoryItem>>()
    val items: LiveData<List<ListStoryItem>> = _item

    private val _itemWithLoc = MutableLiveData<List<ListStoryItem>>()
    val itemsWithLoc: LiveData<List<ListStoryItem>> = _itemWithLoc

    private val _detile = MutableLiveData<Story>()
    val detile: LiveData<Story> = _detile

    val detail: LiveData<PagingData<DetailResponse>> =
        detailRepository.getDetail().cachedIn(viewModelScope)

    init {
        _token.value = tokenManager.getToken().toString()
        getStory(_token.value.toString())
    }

    private fun getStory(tokenManager: String) {
        val client = ApiConfig.getApiService().getStories("Bearer $tokenManager", null, null, null)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    _item.value = response.body()?.listStory
                } else {
                    Log.e("onFailure: ", response.message())
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("onFailure2: ", t.message.toString())
            }
        })
    }

    fun getDetileWithid(id: String) {
        val client = ApiConfig.getApiService()
            .getStoriesId("Bearer ${tokenManager.getToken().toString()}", id)
        client.enqueue(object : Callback<DetileStoryResponse> {
            override fun onResponse(
                call: Call<DetileStoryResponse>,
                response: Response<DetileStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _detile.value = response.body()?.story
                } else {
                    Log.e("onFailure: ", response.message())
                }
            }

            override fun onFailure(call: Call<DetileStoryResponse>, t: Throwable) {
                Log.e("onFailure2: ", t.message.toString())
            }
        })
    }

    fun getDataWithLocation(location: Int) {
        val client = ApiConfig.getApiService()
            .getStories("Bearer ${tokenManager.getToken().toString()}", null, null, location)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    _itemWithLoc.value = response.body()?.listStory
                } else {
                    Log.e("onFailure: ", response.message())
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("onFailure2: ", t.message.toString())
            }
        })
    }

}