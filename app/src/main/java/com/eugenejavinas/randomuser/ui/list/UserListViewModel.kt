package com.eugenejavinas.randomuser.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eugenejavinas.randomuser.common.rx.RxViewModel
import com.eugenejavinas.randomuser.common.rx.SchedulerProvider
import com.eugenejavinas.randomuser.common.utils.Resource
import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
@Inject internal constructor(
    private val userRepository: UserRepository,
    private val schedulerProvider: SchedulerProvider
): RxViewModel() {

    private val usersLiveData = MutableLiveData<Resource<List<User>>>()

    fun getUsersLiveData(): LiveData<Resource<List<User>>> = usersLiveData

    fun fetchUsers(count: Int) {
        userRepository.fetchUsers(count)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnSubscribe { usersLiveData.value = Resource.Loading() }
            .subscribe({ usersLiveData.value = Resource.Success(it) },
                { usersLiveData.value = Resource.Error(it) })
            .bind()
    }
}