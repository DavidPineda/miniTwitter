package com.example.minitwitter.models.response

import com.google.gson.annotations.SerializedName

data class ResponseAuth (

	@SerializedName("token") val token : String,
	@SerializedName("username") val username : String,
	@SerializedName("email") val email : String,
	@SerializedName("photoUrl") val photoUrl : String,
	@SerializedName("created") val created : String,
	@SerializedName("active") val active : Boolean
)