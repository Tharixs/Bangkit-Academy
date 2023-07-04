package com.example.githubusers.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.local.entity.Favorite
import com.example.githubusers.data.remote.FavoriteRepository

class FavoriteAddUpdateViewModel(application : Application) : ViewModel(){
    private val mFavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) = mFavoriteRepository.insert(favorite)

    fun update(favorite: Favorite) = mFavoriteRepository.update(favorite)

    fun delete(favorite: Favorite) = mFavoriteRepository.delete(favorite)

}