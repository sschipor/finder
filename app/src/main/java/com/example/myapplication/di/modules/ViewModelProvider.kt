package com.example.myapplication.di.modules

import com.example.myapplication.di.Provider
import com.example.myapplication.ui.activity.MainActivityViewModel
import com.example.myapplication.ui.fragment.LoginFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class ViewModelProvider {
    companion object : Provider<Module> {
        override fun get(): Module = module {
            viewModel {
                MainActivityViewModel(get(), get())
            }
            viewModel {
                LoginFragmentViewModel(get(), get())
            }
        }

    }
}