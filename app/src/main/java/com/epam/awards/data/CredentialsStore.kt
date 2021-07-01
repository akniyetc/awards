package com.epam.awards.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.epam.awards.domain.entity.Auth
import javax.inject.Inject

const val USER_NAME = "user_name_key"
const val PASSWORD = "password_key"
const val ACCESS_TOKEN = "access_token_key"
const val REFRESH_TOKEN = "refresh_token_key"
const val IS_LOGGED = "is_logged_key"

class CredentialsStore @Inject constructor(
    private val prefs: SharedPreferences
) {

    fun saveLoginCredentials(userName: String, password: String) =
        prefs.edit {
            putString(USER_NAME, userName)
            putString(PASSWORD, password)
        }

    fun username(): String = prefs.getString(USER_NAME, "")!!

    fun password(): String = prefs.getString(PASSWORD, "")!!

    fun saveAuth(auth: Auth) =
        prefs.edit {
            putBoolean(IS_LOGGED, true)
            putString(ACCESS_TOKEN, auth.accessToken)
            putString(REFRESH_TOKEN, auth.refreshToken)
        }

    fun accessToken(): String = prefs.getString(ACCESS_TOKEN, "")!!

    fun refreshToken(): String = prefs.getString(REFRESH_TOKEN, "")!!

    fun isLogged(): Boolean = prefs.getBoolean(IS_LOGGED, false)
}
