package com.example.testcimbapp.feature.home.presentation

import com.example.testcimbapp.feature.home.domain.model.User

data class UserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)