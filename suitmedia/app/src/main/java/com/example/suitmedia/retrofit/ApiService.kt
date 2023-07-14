package com.example.suitmedia.retrofit

import com.example.suitmedia.response.DataItem
import com.example.suitmedia.response.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/users")
    suspend fun getData(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): ResponseData
}