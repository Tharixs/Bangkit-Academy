package com.example.suitmedia.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.suitmedia.database.UserDatabase
import com.example.suitmedia.response.DataItem
import com.example.suitmedia.retrofit.ApiService

class DataRepository(
    private val userDatabase: UserDatabase? = null,
    private val apiService: ApiService
) {

    fun getData(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                PagingDataSource(apiService)
            }
        ).liveData
    }
}