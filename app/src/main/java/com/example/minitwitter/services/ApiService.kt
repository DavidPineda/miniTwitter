package com.example.minitwitter.services

import com.example.minitwitter.models.request.RequestLogin
import com.example.minitwitter.models.request.RequestSignUp
import com.example.minitwitter.models.response.ResponseAuth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("auth/login")
    fun doLogin(@Body requestLogin: RequestLogin): Call<ResponseAuth>

    @POST("auth/signUp")
    fun doSignUp(@Body RequestSignUp: RequestSignUp): Call<ResponseAuth>
}