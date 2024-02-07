package com.example.myapplication.domain.rest.intercetor

import com.example.myapplication.di.InjectionHelper
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "${InjectionHelper.localData.tokenType} ${InjectionHelper.localData.authToken}"
            ).build()
        return chain.proceed(request)
    }
}