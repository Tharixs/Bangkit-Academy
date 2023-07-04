package com.example.storyapp.view.di

import android.content.Context
import android.util.Log
import com.example.storyapp.view.data.DetailRepository
import com.example.storyapp.view.database.DetailDatabase
import com.example.storyapp.view.model.TokenManager
import com.example.storyapp.view.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): DetailRepository {
        val database = DetailDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val tokenManager = TokenManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE))
        Log.e("tokenInjection: ", tokenManager.getToken().toString())
        return DetailRepository(database, apiService,"Bearer ${tokenManager.getToken().toString()}")
    }
}