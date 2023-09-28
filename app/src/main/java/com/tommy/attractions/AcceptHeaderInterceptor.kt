package com.tommy.attractions

import okhttp3.Interceptor
import okhttp3.Response

class AcceptHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(modifiedRequest)
    }
}