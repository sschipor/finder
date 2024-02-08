package com.example.myapplication.di

import com.example.myapplication.di.modules.DataProvider
import com.example.myapplication.di.modules.ViewModelProvider
import org.koin.core.module.Module

class AppModuleProviderImpl : KoinModuleProvider {
    override fun provideDataModule(): Module = DataProvider.get()

    override fun viewModelProvider(): Module = ViewModelProvider.get()
}