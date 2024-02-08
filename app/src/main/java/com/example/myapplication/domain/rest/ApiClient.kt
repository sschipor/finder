package com.example.myapplication.domain.rest

import com.example.myapplication.domain.rest.interceptor.AuthInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    /**
     * Simple http client
     * Can be extended to add interceptor for token expired and refetch a new token
     */
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    val api = Retrofit.Builder()
        .baseUrl("https://api.petfinder.com")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(ApiInterface::class.java)
}