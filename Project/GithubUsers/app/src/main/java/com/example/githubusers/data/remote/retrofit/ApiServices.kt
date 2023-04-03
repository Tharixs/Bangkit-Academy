package com.example.githubusers.data.remote.retrofit

import com.example.githubusers.data.remote.response.DetileUserResponse
import com.example.githubusers.data.remote.response.FollowResponseItem
import com.example.githubusers.data.remote.response.UsersGitResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("search/users")
    fun getUsers(@Query("q") q: String): Call<UsersGitResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetileUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowResponseItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowResponseItem>>
}