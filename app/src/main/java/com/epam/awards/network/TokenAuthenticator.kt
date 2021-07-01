package com.epam.awards.network

import com.epam.awards.data.CredentialsStore
import com.epam.awards.domain.entity.Auth
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

const val TEST_CLIENT = "testclient5013"

class TokenAuthenticator @Inject constructor(
    private val refreshTokenApi: RefreshTokenApi,
    private val credentialsStore: CredentialsStore
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshedAuth = refreshToken()

        return if (refreshedAuth != null) {
            credentialsStore.saveAuth(refreshedAuth)
            response.request.newBuilder()
                .header("client", TEST_CLIENT)
                .header("token", credentialsStore.accessToken())
                .build()
        } else null
    }

    private fun refreshToken(): Auth? = refreshTokenApi
        .refreshToken(credentialsStore.refreshToken(), TEST_CLIENT)
        .blockingGet()
}
