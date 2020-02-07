package com.example.minitwitter.models.response

import com.google.gson.annotations.SerializedName

data class Like (
	@SerializedName("id") val id : Int,
	@SerializedName("username") val username : String,
	@SerializedName("descripcion") val descripcion : String,
	@SerializedName("website") val website : String,
	@SerializedName("photoUrl") val photoUrl : String,
	@SerializedName("created") val created : String
)