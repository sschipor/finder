package com.example.myapplication.di.modules

import com.example.myapplication.data.local.LocalSource
import com.example.myapplication.data.remote.RemoteSource
import com.example.myapplication.data.repo.TheRepository
import com.example.myapplication.data.repo.TheRepositoryImpl
import com.example.myapplication.di.Provider
import com.example.myapplication.domain.local.ApiKeyLocalStore
import com.example.myapplication.domain.rest.ApiClient
import com.example.myapplication.domain.rest.ApiInterface
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module


class DataProvider {

    companion object : Provider<Module> {
        override fun get(): Module = module {
            single { LocalSource() } bind LocalSource::class
            single { ApiClient().api } bind ApiInterface::class
            single { ApiKeyLocalStore() } bind ApiKeyLocalStore::class
            single {
                RemoteSource(
                    apiClient = get(),
                    apiKeyStore = get(),
                )
            }
            single {
                TheRepositoryImpl(
                    remoteSource = get(),
                    localSource = get(),
                )
            } bind TheRepository::class
        }
    }
}