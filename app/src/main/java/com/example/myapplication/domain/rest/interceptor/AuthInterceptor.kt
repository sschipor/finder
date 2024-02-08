package com.example.myapplication.domain.rest.interceptor

import com.example.myapplication.data.local.LocalSource
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthInterceptor : Interceptor, KoinComponent {

    private val localData: LocalSource by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            if (chain.request().url.toUrl().file.contains("/token")) {
                chain.request()
            } else {
                //apply authorization only for non-login requests
                chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "${localData.tokenType} ${localData.authToken}"
                    ).build()
            }
        )
    }
}