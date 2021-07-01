package com.epam.awards.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.epam.awards.common.BaseViewModel
import com.epam.awards.common.SchedulersProvider
import com.epam.awards.domain.LoginUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState> get() = _viewState

    fun login(userName: String, password: String) {
        if (isCorrectInput(userName, password)) {
            loginUseCase.login(userName, password)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.main())
                .doOnSubscribe { _viewState.value = LoginViewState.Loading }
                .subscribe(
                    { _viewState.value = LoginViewState.Signed("Success") },
                    { _viewState.value = LoginViewState.Error }
                )
                .selfDispose()
        } else {
            handleInputError(userName, password)
        }
    }

    private fun isCorrectInput(userName: String, password: String): Boolean =
        userName.isNotEmpty() && password.isNotEmpty()

    private fun handleInputError(userName: String, password: String) {
        when {
            userName.isEmpty() -> _viewState.value =
                LoginViewState.InputError(InputErrorType.WRONG_USER_NAME)
            password.isEmpty() -> _viewState.value =
                LoginViewState.InputError(InputErrorType.WRONG_PASSWORD)
        }
    }
}
