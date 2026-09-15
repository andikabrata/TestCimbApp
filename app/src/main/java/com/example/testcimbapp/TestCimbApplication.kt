package com.example.testcimbapp

import android.app.Application
import com.example.testcimbapp.core.network.networkModule
import com.example.testcimbapp.di.repositoryModule
import com.example.testcimbapp.di.useCaseModule
import com.example.testcimbapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestCimbApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestCimbApplication)

            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}