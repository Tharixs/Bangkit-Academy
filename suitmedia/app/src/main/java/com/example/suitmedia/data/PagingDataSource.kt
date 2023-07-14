package com.example.suitmedia.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.suitmedia.response.DataItem
import com.example.suitmedia.retrofit.ApiService

class PagingDataSource(private val apiService: ApiService) : PagingSource<Int, DataItem>() {

    private companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorposition ->
            val anchorPage = state.closestPageToPosition(anchorposition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.getData(position, params.loadSize)

            val data = response.data

            LoadResult.Page(
                data = data!!.filterNotNull(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }

    }


}