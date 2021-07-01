package com.epam.awards.presentation.login

sealed class LoginViewState {
    data class Signed(val message: String): LoginViewState()
    object Loading: LoginViewState()
    data class InputError(val type: InputErrorType): LoginViewState()
    object Error: LoginViewState()
}

enum class InputErrorType {
    WRONG_USER_NAME, WRONG_PASSWORD
}
