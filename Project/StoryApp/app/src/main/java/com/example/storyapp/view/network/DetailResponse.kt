package com.example.storyapp.view.network

import android.telecom.Call
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DetailStoriesResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<DetailResponse>
)

@Entity(tableName = "detail")
data class DetailResponse(

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: String,

    @field:SerializedName("lat")
    val lat: String,
)