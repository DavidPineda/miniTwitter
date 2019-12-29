package com.example.minitwitter.models.request

import com.google.gson.annotations.SerializedName

data class RequestLogin (

	@SerializedName("email") val email : String,
	@SerializedName("password") val password : String
)