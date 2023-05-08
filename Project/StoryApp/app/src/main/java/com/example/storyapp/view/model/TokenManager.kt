package com.example.storyapp.view.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TokenManager(private val sharedPreferences: SharedPreferences?) {

    private val _livedataToken = MutableLiveData<String?>()
    val livedataToken: LiveData<String?> = _livedataToken

    companion object {
        fun getInstance(sharedPreferences: SharedPreferences?): TokenManager {
            return TokenManager(sharedPreferences!!)
        }

        const val TOKEN_KEY = "TOKEN"
    }

    fun saveToken(token: String) {
        sharedPreferences?.edit()?.putString(TOKEN_KEY, token)?.apply()
        _livedataToken.value = token
    }

    fun getToken(): String? {
        return sharedPreferences?.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences?.edit()?.remove(TOKEN_KEY)?.apply()
        _livedataToken.postValue(null)
    }
}