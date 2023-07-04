package com.example.mafilmapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    val name : String,
    val desk : String,
    val photo : String,
    val star : String,
    val time : String,
    val sutradara : String,
    val sinopsis : String,
    val rilis : String,
):Parcelable


