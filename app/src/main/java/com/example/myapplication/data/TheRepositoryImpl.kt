package com.example.myapplication.data

import com.example.myapplication.data.local.LocalSource
import com.example.myapplication.data.mapper.AnimalsDtoToDataMapper
import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.domain.local.ApiKeyLocalStore
import com.example.myapplication.domain.rest.ApiInterface
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TheRepositoryImpl(
    val apiClient: ApiInterface,
    val apiKeyStore: ApiKeyLocalStore,
    val localSource: LocalSource,
) : TheRepository {

    override fun loginUser(): Completable {
        return apiClient.loginUser(
            clientId = apiKeyStore.getApiKey(),
            clientSecret = apiKeyStore.getSecretKey(),
        ).subscribeOn(Schedulers.io())
            .flatMapCompletable {
                Completable.fromAction {
                    localSource.authToken = it.accessToken
                    localSource.tokenType = it.tokenType
                }
            }
    }

    override fun getAnimals(pageNo: Int): Single<AnimalsList> {
        return apiClient.getAnimals(page = pageNo)
            .subscribeOn(Schedulers.io())
            .map(AnimalsDtoToDataMapper())
    }

    override fun getAnimalDetails(id: Long): Single<AnimalData> {
        return Single.error(Throwable("dsds"))
    }
}