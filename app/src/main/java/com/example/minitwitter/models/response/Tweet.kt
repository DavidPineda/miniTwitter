package com.example.minitwitter.models.response

import com.google.gson.annotations.SerializedName

data class Tweet (
    @SerializedName("id") val id : Int,
    @SerializedName("mensaje") val mensaje : String,
    @SerializedName("likes") val likes : List<Like>,
    @SerializedName("user") val user : User
)
