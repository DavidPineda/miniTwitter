package com.example.minitwitter.services

import com.example.minitwitter.common.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
         var instance: ApiClient? = null
            get () {
                if (field == null) {
                    field = ApiClient()
                }
                return  field
            }
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_MINI_TWITTER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}