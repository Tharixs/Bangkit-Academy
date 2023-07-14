package com.example.suitmedia.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "data")
data class DataItem(

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("last_name")
    val lastName: String,

    @field:SerializedName("avatar")
    val avatar: String,

    @field:SerializedName("first_name")
    val firstName: String,

    @field:SerializedName("email")
    val email: String
)