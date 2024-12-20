package com.eugenejavinas.randomuser.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eugenejavinas.randomuser.common.lifecycle.RxViewModel
import com.eugenejavinas.randomuser.common.utils.Resource
import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
@Inject internal constructor(
    private val userRepository: UserRepository
): RxViewModel() {

    private val usersLiveData = MutableLiveData<Resource<List<User>>>()

    fun getUsersLiveData(): LiveData<Resource<List<User>>> = usersLiveData

    fun fetchUsers(count: Int) {
        userRepository.fetchUsers(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { usersLiveData.value = Resource.Loading() }
            .subscribe({ usersLiveData.value = Resource.Success(it) },
                { usersLiveData.value = Resource.Error(it) })
            .bind()
    }
}