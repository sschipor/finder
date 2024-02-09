package com.example.myapplication.data.remote

import com.example.myapplication.domain.local.ApiKeyLocalStore
import com.example.myapplication.domain.rest.ApiInterface
import com.example.myapplication.domain.rest.dto.AnimalsResponseDto
import com.example.myapplication.domain.rest.dto.LoginUserDto
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

internal class RemoteSource(
    private val apiClient: ApiInterface,
    private val apiKeyStore: ApiKeyLocalStore,
) {

    fun loginUser(): Single<LoginUserDto> {
        return apiClient.loginUser(
            clientId = apiKeyStore.getApiKey(),
            clientSecret = apiKeyStore.getSecretKey(),
        ).subscribeOn(Schedulers.io())
    }

    fun getAnimals(pageNo: Int, userLocation: String?): Single<AnimalsResponseDto> {
        return apiClient.getAnimals(page = pageNo, userLocation = userLocation)
            .subscribeOn(Schedulers.io())
    }
}