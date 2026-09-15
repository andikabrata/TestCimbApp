package com.example.testcimbapp.feature.home.data.mapper

import com.example.testcimbapp.feature.home.data.remote.UserResponse
import com.example.testcimbapp.feature.home.domain.model.Address
import com.example.testcimbapp.feature.home.domain.model.Company
import com.example.testcimbapp.feature.home.domain.model.Geo
import com.example.testcimbapp.feature.home.domain.model.User

fun UserResponse.toDomain(): User {
    return User(
        id = id,
        name = name,
        username = username,
        email = email,
        address = Address(
            street = address.street,
            suite = address.suite,
            city = address.city,
            zipcode = address.zipcode,
            geo = Geo(
                lat = address.geo.lat,
                lng = address.geo.lng
            )
        ),
        phone = phone,
        website = website,
        company = Company(
            name = company.name,
            catchPhrase = company.catchPhrase,
            bs = company.bs
        )
    )
}