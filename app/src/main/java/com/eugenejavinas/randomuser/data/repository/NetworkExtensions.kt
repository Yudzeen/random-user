package com.eugenejavinas.randomuser.data.repository

import com.eugenejavinas.randomuser.data.model.Address
import com.eugenejavinas.randomuser.data.model.Name
import com.eugenejavinas.randomuser.data.model.Picture
import com.eugenejavinas.randomuser.data.model.Street
import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.data.network.UserResponse

fun UserResponse.toModel(): User {
    return User(
        name = Name(
            first = name?.first ?: "",
            last = name?.last ?: ""
        ),
        address = Address(
            street = Street(
                number = location?.street?.number ?: -1,
                name = location?.street?.name ?: ""
            ),
            city = location?.city ?: "",
            state = location?.state ?: "",
            country = location?.country ?: "",
        ),
        gender = gender ?: "",
        email = email ?: "",
        picture = Picture(
            large = picture?.large ?: "",
            medium = picture?.medium ?: "",
            thumbnail = picture?.thumbnail ?: ""
        )
    )
}