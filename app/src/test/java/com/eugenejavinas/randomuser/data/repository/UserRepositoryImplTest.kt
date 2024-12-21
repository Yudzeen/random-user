package com.eugenejavinas.randomuser.data.repository

import com.eugenejavinas.randomuser.data.network.FetchUsersResponse
import com.eugenejavinas.randomuser.data.network.Name
import com.eugenejavinas.randomuser.data.network.RandomUserApi
import com.eugenejavinas.randomuser.data.network.UserResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepositoryImpl
    @MockK lateinit var randomUserApi: RandomUserApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepository = UserRepositoryImpl(randomUserApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Should map api response to user model`() {
        val results = sampleUserListResponse()
        val response = FetchUsersResponse(results = results)
        every { randomUserApi.fetchUsers(any()) } returns Single.just(response)

        userRepository.fetchUsers(3)
            .test()
            .assertValue { users ->
                users[0].name.first == results[0].name?.first && users[0].name.last == results[0].name?.last
                users[1].name.first == results[1].name?.first && users[1].name.last == results[1].name?.last
                users[2].name.first == results[2].name?.first && users[2].name.last == results[2].name?.last
            }
    }

    @Test
    fun `Should return empty list when api result is null`() {
        val response = FetchUsersResponse(results = null)
        every { randomUserApi.fetchUsers(any()) } returns Single.just(response)

        userRepository.fetchUsers(3)
            .test()
            .assertValue(emptyList())
    }

    private fun sampleUserListResponse(): List<UserResponse> {
        return listOf(
            UserResponse(name = Name(first = "Galileo", last = "Galilei")),
            UserResponse(name = Name(first = "Isaac", last = "Newton")),
            UserResponse(name = Name(first = "Albert", last = "Einstein")),
        )
    }

}