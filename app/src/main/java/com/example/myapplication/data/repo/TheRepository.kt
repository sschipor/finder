package com.example.myapplication.data.repo

import com.example.myapplication.data.model.AnimalsList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface TheRepository {

    fun isUserLoggedIn(): Flowable<Boolean>

    fun loginUser(): Completable

    fun getAnimals(pageNo: Int, userLocation: String?): Single<AnimalsList>
}