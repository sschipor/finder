package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppModuleProviderImpl
import com.example.myapplication.di.KoinModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModuleProvider: KoinModuleProvider = AppModuleProviderImpl()
        startKoin {
            logger(
                AndroidLogger(Level.DEBUG)
            )
            androidContext(this@AppClass)
            modules(appModuleProvider.provideDataModule(), appModuleProvider.viewModelProvider())
        }
    }
}