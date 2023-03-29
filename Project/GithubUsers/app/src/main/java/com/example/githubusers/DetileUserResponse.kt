package com.example.githubusers

import com.google.gson.annotations.SerializedName

data class DetileUserResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("email")
	val email: Any,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

)
