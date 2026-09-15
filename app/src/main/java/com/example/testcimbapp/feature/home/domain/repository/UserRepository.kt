package com.example.testcimbapp.feature.home.domain.repository

import com.example.testcimbapp.feature.home.domain.model.User

interface  UserRepository {
    suspend fun getUsers() : Result<List<User>>
}