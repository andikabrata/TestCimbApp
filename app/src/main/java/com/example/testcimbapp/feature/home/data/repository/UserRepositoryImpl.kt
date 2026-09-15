package com.example.testcimbapp.feature.home.data.repository

import com.example.testcimbapp.core.network.ApiService
import com.example.testcimbapp.feature.home.data.mapper.toDomain
import com.example.testcimbapp.feature.home.domain.model.User
import com.example.testcimbapp.feature.home.domain.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return runCatching {
            apiService.getUsers().map { it.toDomain() }
        }
    }
}