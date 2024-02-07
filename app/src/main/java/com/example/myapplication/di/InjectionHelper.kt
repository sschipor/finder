package com.example.myapplication.di

import com.example.myapplication.data.TheRepository
import com.example.myapplication.data.TheRepositoryImpl
import com.example.myapplication.data.local.LocalSource
import com.example.myapplication.domain.local.ApiKeyLocalStore
import com.example.myapplication.domain.rest.ApiClient

/**
 * Simulate an injector (Dagger, Hilt)
 */
object InjectionHelper {
    val localData = LocalSource()
    private val apiClient = ApiClient.api
    private val apiKeyLocalstore = ApiKeyLocalStore()
    val theRepository: TheRepository = TheRepositoryImpl(
        apiClient = apiClient,
        apiKeyStore = apiKeyLocalstore,
        localSource = localData,
    )
}