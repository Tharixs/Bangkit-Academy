package com.example.githubusers.data.remote

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubusers.data.local.entity.Favorite
import com.example.githubusers.data.local.room.FavoriteDao
import com.example.githubusers.data.local.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorite()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun update(favorite: Favorite) {
        executorService.execute { mFavoriteDao.update(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

}