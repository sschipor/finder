package com.example.myapplication.domain.rest

import com.example.myapplication.domain.rest.dto.AnimalsResponseDto
import com.example.myapplication.domain.rest.dto.LoginUserDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST("/v2/oauth2/token")
    fun loginUser(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = "client_credentials",
    ): Single<LoginUserDto>

    @GET("/v2/animals")
    fun getAnimals(
        @Query("page") page: Int,
    ): Single<AnimalsResponseDto>
}