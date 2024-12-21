package com.eugenejavinas.randomuser.data.repository

import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.data.network.RandomUserApi
import io.reactivex.rxjava3.core.Single

class UserRepositoryImpl(
    private val randomUserApi: RandomUserApi
): UserRepository {

    override fun fetchUsers(count: Int): Single<List<User>> {
        return randomUserApi.fetchUsers(count).map { response ->
            response.results?.map { it.toModel() } ?: emptyList()
        }
    }
}