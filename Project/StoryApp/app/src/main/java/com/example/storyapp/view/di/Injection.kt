package com.example.storyapp.view.di

import android.content.Context
import com.example.storyapp.view.data.DetailRepository
import com.example.storyapp.view.database.DetailDatabase
import com.example.storyapp.view.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): DetailRepository {
        val database = DetailDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return DetailRepository(database, apiService)
    }
}