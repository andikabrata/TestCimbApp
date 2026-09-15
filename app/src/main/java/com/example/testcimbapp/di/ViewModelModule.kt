package com.example.testcimbapp.di

import com.example.testcimbapp.feature.home.presentation.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserViewModel(
            getUserUseCase = get()
        )
    }
}