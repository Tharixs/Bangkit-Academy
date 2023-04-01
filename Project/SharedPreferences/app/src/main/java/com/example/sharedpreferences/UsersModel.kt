package com.example.sharedpreferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersModel(
    var name: String? = null,
    var email: String? = null,
    var no: String? = null,
    var age: Int = 0,
    var isLove: Boolean = false
) : Parcelable