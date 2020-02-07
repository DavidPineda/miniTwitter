package com.example.minitwitter.services

import com.example.minitwitter.models.response.Tweet
import retrofit2.Call
import retrofit2.http.GET

interface AuthApiService {

    @GET("tweets/all")
    fun getAllTweets(): Call<List<Tweet>>
}