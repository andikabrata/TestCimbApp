package com.example.testcimbapp.feature.home.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressResponse,
    val phone: String,
    val website: String,
    val company: CompanyResponse
)

@Serializable
data class AddressResponse (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoResponse
)

@Serializable
data class GeoResponse(
    val lat: String,
    val lng: String
)

@Serializable
data class CompanyResponse(
    val name: String,
    val catchPhrase: String,
    val bs: String
)
