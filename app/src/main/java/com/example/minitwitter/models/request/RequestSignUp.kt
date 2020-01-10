package com.example.minitwitter.models.request

import com.google.gson.annotations.SerializedName

data class RequestSignUp (

	@SerializedName("username") val username : String,
	@SerializedName("email") val email : String,
	@SerializedName("password") val password : String,
	@SerializedName("code") val code : String
)