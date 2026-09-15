package com.example.testcimbapp.di

import com.example.testcimbapp.feature.home.domain.usecase.GetUsersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetUsersUseCase(
            repository = get()
        )
    }
}