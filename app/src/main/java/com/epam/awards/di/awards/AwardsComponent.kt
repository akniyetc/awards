package com.epam.awards.di.awards

import com.epam.awards.data.awards.AwardsApi
import com.epam.awards.data.awards.AwardsRepositoryImpl
import com.epam.awards.di.FragmentScope
import com.epam.awards.di.main.quilifier.MainRetrofit
import com.epam.awards.domain.repo.AwardsRepository
import com.epam.awards.ui.AwardsFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@FragmentScope
@Component(
    dependencies = [AwardsDependencies::class],
    modules = [AwardsModule::class]
)
interface AwardsComponent {
    fun inject(awardsFragment: AwardsFragment)
}

@Module
private abstract class AwardsModule {

    @Binds
    abstract fun bindAwardsRepo(awardsRepositoryImpl: AwardsRepositoryImpl): AwardsRepository

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideAwardsApi(@MainRetrofit retrofit: Retrofit): AwardsApi =
            retrofit.create(AwardsApi::class.java)
    }
}
