package com.epam.awards.data.login

import com.epam.awards.domain.entity.Auth
import com.epam.awards.network.TEST_CLIENT
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LoginApi {

    @GET("ws/authservice/getAccessToken/{user_name}")
    fun login(
        @Path("user_name") userName: String,
        @Header("pwd") password: String,
        @Header("client") client: String = TEST_CLIENT
    ): Single<Auth>
}
