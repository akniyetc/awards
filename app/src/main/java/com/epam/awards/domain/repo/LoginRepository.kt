package com.epam.awards.domain.repo

import io.reactivex.rxjava3.core.Completable

interface LoginRepository {
    fun login(userName: String, password: String): Completable
}
