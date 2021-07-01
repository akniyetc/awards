package com.epam.awards.domain

import com.epam.awards.domain.repo.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    fun login(userName: String, password: String) = loginRepository.login(userName, password)
}
