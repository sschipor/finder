package com.example.myapplication.domain.rest.dto

import com.google.gson.annotations.SerializedName

data class LoginUserDto(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("token")
    val accessToken: String,
)
