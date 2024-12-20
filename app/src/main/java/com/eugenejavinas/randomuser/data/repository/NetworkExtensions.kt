package com.eugenejavinas.randomuser.data.repository

import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.data.network.UserNetwork

fun UserNetwork.toModel(): User {
    return User(
        name = User.Name(
            first = name?.first ?: "",
            last = name?.last ?: ""
        ),
        address = User.Address(
            street = User.Address.Street(
                number = location?.street?.number ?: -1,
                name = location?.street?.name ?: ""
            ),
            city = location?.city ?: "",
            state = location?.state ?: "",
            country = location?.country ?: "",
        ),
        gender = gender ?: "",
        email = email ?: "",
        landline = landline ?: "",
        mobile = mobile ?: "",
        picture = User.Picture(
            large = picture?.large ?: "",
            medium = picture?.medium ?: "",
            thumbnail = picture?.thumbnail ?: ""
        )
    )
}