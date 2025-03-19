package com.example.mycatapp.data.interceptors

import com.example.mycatapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AddInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            addHeader("x-api-key", BuildConfig.apikey)
        }.build().also {
            return chain.proceed(it)
        }
    }
}