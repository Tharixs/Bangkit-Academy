package com.example.storyapp.view.response

import com.google.gson.annotations.SerializedName

data class StoriesResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>
)
data class ListStoryItem(

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

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: String,
)

