package com.epam.awards.di.awards

import com.epam.awards.common.ComponentDependencies
import com.epam.awards.di.main.quilifier.MainRetrofit
import retrofit2.Retrofit

interface AwardsDependencies : ComponentDependencies {
    @MainRetrofit
    fun retrofit(): Retrofit
}
