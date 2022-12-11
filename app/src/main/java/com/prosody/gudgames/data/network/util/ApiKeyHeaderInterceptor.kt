package com.prosody.gudgames.data.network.util

import com.prosody.gudgames.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyHeaderInterceptor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Request.Builder().addHeader("x-api-key", BuildConfig.apiKey)
        return chain.proceed(request)
    }
}