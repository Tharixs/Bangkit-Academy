package com.example.githubusers

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
                        "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"
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