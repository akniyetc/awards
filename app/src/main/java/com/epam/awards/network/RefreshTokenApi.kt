package com.epam.awards.network

import com.epam.awards.domain.entity.Auth
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface RefreshTokenApi {

    @GET("/ws/authservice/refreshAccessToken")
    fun refreshToken(
        @Header("refreshToken") refreshToken: String,
        @Header("client") userName: String
    ): Single<Auth>
}
