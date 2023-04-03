package com.example.githubusers.data.remote.retrofit

import com.example.githubusers.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class ApiConfig {
    companion object {
        fun getApiService(): ApiServices {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requiresHeaders = req.newBuilder()
                    .addHeader(
                        "Authorization",
                        BuildConfig.API_KEY
                    )
                    .build()
                chain.proceed(requiresHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiServices::class.java)
        }
    }
}