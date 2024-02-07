package com.example.myapplication.domain.rest

import com.example.myapplication.domain.rest.intercetor.AuthInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    /**
     * Simple http client
     * Can be extended to add interceptor for token expired and refetch a new token
     */
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    val api = Retrofit.Builder()
        .baseUrl("https://api.petfinder.com")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(ApiInterface::class.java)
}