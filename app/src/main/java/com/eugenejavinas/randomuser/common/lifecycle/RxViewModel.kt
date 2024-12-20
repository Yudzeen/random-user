package com.eugenejavinas.randomuser.common.lifecycle

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class RxViewModel: ViewModel() {

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun Disposable.bind() {
        disposables.add(this)
    }

}