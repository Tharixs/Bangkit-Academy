package com.example.storyapp.view.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.view.database.DetailDatabase
import com.example.storyapp.view.network.DetailResponse
import com.example.storyapp.view.response.DetileStoryResponse
import com.example.storyapp.view.response.ListStoryItem
import com.example.storyapp.view.response.StoriesResponse
import com.example.storyapp.view.retrofit.ApiService

class DetailRepository(
    private val database: DetailDatabase? = null,
    private val apiService: ApiService,
    private val tokenManager: String
) {
    fun getDetail(): LiveData<PagingData<DetailResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                DetailPagingSource(apiService, tokenManager)
            }
        ).liveData
    }
}