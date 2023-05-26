package com.example.jetfood.di

import com.example.jetfood.data.FoodRepository

object Injection {

    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}