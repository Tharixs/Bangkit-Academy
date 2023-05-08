package com.example.storyapp.view.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.view.model.TokenManager
import com.example.storyapp.view.network.DetailResponse
import com.example.storyapp.view.retrofit.ApiService

class DetailPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, DetailResponse>() {

    private lateinit var tokenManager: TokenManager

    private companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DetailResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailResponse> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            Log.e("tokenSource", tokenManager.getToken().toString())
            val responseData = apiService.getStoriesItem(
                "Bearer ${tokenManager.getToken()}",
                position,
                params.loadSize,
                1
            )
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}