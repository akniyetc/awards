package com.epam.awards.data.login

import com.epam.awards.data.CredentialsStore
import com.epam.awards.domain.repo.LoginRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val credentialsStore: CredentialsStore
) : LoginRepository {

    override fun login(userName: String, password: String): Completable =
        loginApi.login(userName, password)
            .doOnSuccess { credentialsStore.saveAuth(it) }
            .ignoreElement()
}
