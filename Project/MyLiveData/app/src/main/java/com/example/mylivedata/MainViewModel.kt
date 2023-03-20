package com.example.mylivedata

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class MainViewModel : ViewModel() {
    companion object {
        private const val ONE_SECCOND = 1000
    }

    private val mInitializeTime = SystemClock.elapsedRealtime()
    private val mElipsedTime = MutableLiveData<Long?>()

    init {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newVal = (SystemClock.elapsedRealtime() - mInitializeTime) / 1000
                mElipsedTime.postValue(newVal)
            }
        }, ONE_SECCOND.toLong(), ONE_SECCOND.toLong())
    }

    fun getElipsedTime(): LiveData<Long?> {
        return mElipsedTime
    }
}