package com.epam.awards.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    protected fun Disposable.selfDispose() {
        disposables.add(this)
    }
}
