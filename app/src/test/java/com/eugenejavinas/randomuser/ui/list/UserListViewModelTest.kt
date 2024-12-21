package com.eugenejavinas.randomuser.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eugenejavinas.randomuser.common.rx.SchedulerProvider
import com.eugenejavinas.randomuser.common.utils.Resource
import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.data.repository.UserRepository
import com.eugenejavinas.randomuser.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel
    @MockK lateinit var userRepository: UserRepository
    @MockK lateinit var schedulerProvider: SchedulerProvider

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { schedulerProvider.io() } returns Schedulers.trampoline()
        every { schedulerProvider.mainThread() } returns Schedulers.trampoline()
        viewModel = UserListViewModel(userRepository, schedulerProvider)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Should emit success resource when fetch users is successful`() {
        // Given
        val users: List<User> = listOf(mockk(), mockk(), mockk())
        every { userRepository.fetchUsers(any()) } returns Single.just(users)

        // When
        viewModel.fetchUsers(10)

        // Then
        viewModel.getUsersLiveData().getOrAwaitValue().let {
            if (it is Resource.Success) {
                assert(it.data == users)
            } else {
                fail()
            }
        }
    }

    @Test
    fun `Should emit error resource when fetch users failed`() {
        // Given
        val exception = Exception("Error")
        every { userRepository.fetchUsers(any()) } returns Single.error(exception)

        // When
        viewModel.fetchUsers(10)

        // Then
        viewModel.getUsersLiveData().getOrAwaitValue().let {
            if (it is Resource.Error) {
                assert(it.error == exception)
            } else {
                fail()
            }
        }
    }
}