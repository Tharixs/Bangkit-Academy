package com.example.notesapp.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun currentData(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)

    }
}