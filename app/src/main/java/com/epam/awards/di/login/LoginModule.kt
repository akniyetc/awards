package com.epam.awards.di.login

import com.epam.awards.data.login.LoginApi
import com.epam.awards.data.login.LoginRepositoryImpl
import com.epam.awards.di.main.quilifier.MainRetrofit
import com.epam.awards.domain.repo.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepo(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideLoginApi(@MainRetrofit retrofit: Retrofit): LoginApi =
            retrofit.create(LoginApi::class.java)
    }
}
