package com.example.minitwitter.services

import com.example.minitwitter.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthApiClient {
    companion object {
        var instance: AuthApiClient? = null
            get () {
                if (field == null) {
                    field = AuthApiClient()
                }
                return  field
            }
    }

    private var okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private var client: OkHttpClient
    private var retrofit: Retrofit

    init {
        okHttpClientBuilder.addInterceptor(AuthInterceptor())
        client = okHttpClientBuilder.build()
        retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_MINI_TWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
}