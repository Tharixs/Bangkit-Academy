package com.example.storyapp.view.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storyapp.view.network.DetailResponse


@Database(entities = [DetailResponse::class], version = 1)

abstract class DetailDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: DetailDatabase? = null

        @JvmStatic
        fun getDatabase(contex: Context): DetailDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: androidx.room.Room.databaseBuilder(
                    contex.applicationContext,
                    DetailDatabase::class.java,
                    "detail_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}