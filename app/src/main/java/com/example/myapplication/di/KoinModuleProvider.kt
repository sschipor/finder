package com.example.myapplication.di

import org.koin.core.module.Module

interface KoinModuleProvider {
    fun provideDataModule(): Module

    fun viewModelProvider(): Module
}