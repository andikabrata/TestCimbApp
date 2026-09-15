package com.example.testcimbapp.di

import com.example.testcimbapp.feature.home.domain.repository.UserRepository
import com.example.testcimbapp.feature.home.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            apiService = get()
        )
    }
}