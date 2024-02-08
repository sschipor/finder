package com.example.myapplication.domain.rest.dto

import com.google.gson.annotations.SerializedName

data class LoginUserDto(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("access_token")
    val accessToken: String,
)
