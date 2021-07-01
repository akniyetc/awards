package com.epam.awards.di.splash

import com.epam.awards.ui.SplashFragment
import com.epam.awards.di.FragmentScope
import dagger.Component

@FragmentScope
@Component(
    dependencies = [SplashDependencies::class]
)
interface SplashComponent {
    fun inject(splashFragment: SplashFragment)
}
