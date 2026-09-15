package com.example.testcimbapp.core.network

import com.example.testcimbapp.feature.home.data.remote.UserResponse
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}