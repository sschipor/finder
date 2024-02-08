package com.example.myapplication.data.local

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class LocalSource {

    var tokenType: String? = null
    var authToken: String? = null
    private val userLoginStatus: Subject<Boolean> = BehaviorSubject.createDefault(false)

    fun isUserLoggedIn(): Flowable<Boolean> {
        return userLoginStatus.toFlowable(BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
    }

    fun updateToken(tokenType: String?, authToken: String?) {
        this.tokenType = tokenType
        this.authToken = authToken
        userLoginStatus.onNext(tokenType.isNullOrEmpty().not() && authToken.isNullOrEmpty().not())
    }
}