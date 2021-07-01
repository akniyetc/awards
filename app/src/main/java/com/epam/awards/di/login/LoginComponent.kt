package com.epam.awards.di.login

import com.epam.awards.ui.LoginFragment
import com.epam.awards.di.FragmentScope
import dagger.Component

@FragmentScope
@Component(
    dependencies = [LoginDependencies::class],
    modules = [LoginModule::class]
)
interface LoginComponent {

    fun inject(loginFragment: LoginFragment)

    @Component.Factory
    interface Factory {
        fun create(deps: LoginDependencies): LoginComponent
    }
}
