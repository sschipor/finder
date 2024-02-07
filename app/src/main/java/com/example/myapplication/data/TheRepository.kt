package com.example.myapplication.data

import com.example.myapplication.data.model.AnimalsList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface TheRepository {

    fun loginUser(): Completable

    fun getAnimals(pageNo: Int): Single<AnimalsList>
}