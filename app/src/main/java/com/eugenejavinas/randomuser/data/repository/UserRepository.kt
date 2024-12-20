package com.eugenejavinas.randomuser.data.repository

import com.eugenejavinas.randomuser.data.model.User
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun fetchUsers(count: Int): Single<List<User>>

}