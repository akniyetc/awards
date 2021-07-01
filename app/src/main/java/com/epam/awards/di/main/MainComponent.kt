package com.epam.awards.di.main

import android.content.Context
import android.content.SharedPreferences
import com.epam.awards.MainActivity
import com.epam.awards.common.ComponentDependencies
import com.epam.awards.common.ComponentDependenciesKey
import com.epam.awards.data.CredentialsStore
import com.epam.awards.di.awards.AwardsDependencies
import com.epam.awards.di.login.LoginDependencies
import com.epam.awards.di.main.quilifier.BaseRetrofit
import com.epam.awards.di.main.quilifier.MainRetrofit
import com.epam.awards.di.splash.SplashDependencies
import com.epam.awards.network.TEST_CLIENT
import com.epam.awards.network.RefreshTokenApi
import com.epam.awards.network.TokenAuthenticator
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class MainActivityScope

@MainActivityScope
@Component(
    modules = [
        ComponentDependenciesModule::class,
        NetworkModule::class,
        ActivityModule::class
    ]
)
interface MainComponent : SplashDependencies, LoginDependencies, AwardsDependencies {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }
}

@Module
private abstract class ComponentDependenciesModule private constructor() {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(LoginDependencies::class)
    abstract fun provideLoginDependencies(component: MainComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(SplashDependencies::class)
    abstract fun provideSplashDependencies(component: MainComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(AwardsDependencies::class)
    abstract fun provideAwardsDependencies(component: MainComponent): ComponentDependencies
}

@Module
private object NetworkModule {

    @JvmStatic
    @Provides
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @JvmStatic
    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor,
        authenticator: TokenAuthenticator,
        credentialsStore: CredentialsStore
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("client", TEST_CLIENT)
                    .header("token", credentialsStore.accessToken())
                    .build()
                chain.proceed(request)
            }
            .build()

    @JvmStatic
    @Provides
    @BaseRetrofit
    fun provideBaseRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://mobile.workhuman.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @JvmStatic
    @Provides
    @MainRetrofit
    fun provideMainRetrofit(
        @BaseRetrofit retrofit: Retrofit,
        okHttpClient: OkHttpClient
    ): Retrofit = retrofit.newBuilder()
        .client(okHttpClient)
        .build()

    @JvmStatic
    @Provides
    fun provideRefreshTokenApi(@BaseRetrofit retrofit: Retrofit): RefreshTokenApi =
        retrofit.create(RefreshTokenApi::class.java)
}

@Module
private object ActivityModule {

    @Provides
    @JvmStatic
    fun providePrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("Credentials", Context.MODE_PRIVATE)
}
