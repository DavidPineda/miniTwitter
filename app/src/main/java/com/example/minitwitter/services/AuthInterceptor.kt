package com.example.minitwitter.services

import com.example.minitwitter.common.Constants
import com.example.minitwitter.common.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = SharedPreferencesManager.getSomeStringValue(Constants.PREF_AUTH_TOKEN)
        val request: Request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}