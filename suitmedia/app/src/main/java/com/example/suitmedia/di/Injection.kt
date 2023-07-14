package com.example.suitmedia.di

import android.content.Context
import com.example.suitmedia.data.DataRepository
import com.example.suitmedia.database.UserDatabase
import com.example.suitmedia.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return DataRepository(database, apiService)
    }
}