package com.epam.awards.di.login

import android.content.SharedPreferences
import com.epam.awards.common.ComponentDependencies
import com.epam.awards.di.main.quilifier.MainRetrofit
import retrofit2.Retrofit

interface LoginDependencies : ComponentDependencies {

    @MainRetrofit
    fun retrofit(): Retrofit
    fun sharedPrefs(): SharedPreferences
}
