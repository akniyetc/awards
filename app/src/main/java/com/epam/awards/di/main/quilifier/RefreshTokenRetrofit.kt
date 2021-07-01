package com.epam.awards.di.main.quilifier

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class RefreshTokenRetrofit
