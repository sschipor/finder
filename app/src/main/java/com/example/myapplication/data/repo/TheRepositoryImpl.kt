package com.example.myapplication.data.repo

import com.example.myapplication.data.local.LocalSource
import com.example.myapplication.data.mapper.AnimalsDtoToDataMapper
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.data.remote.RemoteSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

internal class TheRepositoryImpl(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
) : TheRepository {

    override fun isUserLoggedIn(): Flowable<Boolean> {
        return localSource.isUserLoggedIn()
    }

    override fun loginUser(): Completable {
        return remoteSource.loginUser()
            .flatMapCompletable {
                Completable.fromAction {
                    localSource.updateToken(tokenType = it.tokenType, authToken = it.accessToken)
                }
            }
    }

    override fun getAnimals(pageNo: Int): Single<AnimalsList> {
        return remoteSource.getAnimals(pageNo = pageNo)
            .map(AnimalsDtoToDataMapper())
    }
}