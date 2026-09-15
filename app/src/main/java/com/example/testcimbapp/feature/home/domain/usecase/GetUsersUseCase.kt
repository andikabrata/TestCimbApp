package com.example.testcimbapp.feature.home.domain.usecase

import com.example.testcimbapp.feature.home.domain.model.User
import com.example.testcimbapp.feature.home.domain.repository.UserRepository

class GetUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>>{
        return repository.getUsers()
    }
}