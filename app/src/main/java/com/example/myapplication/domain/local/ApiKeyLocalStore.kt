package com.example.myapplication.domain.local

class ApiKeyLocalStore {
    init {
        try {
            System.loadLibrary("native-lib")
        } catch (e: Exception) {
            //ignore
        }
    }

    external fun getSecretKey(): String

    external fun getApiKey(): String
}