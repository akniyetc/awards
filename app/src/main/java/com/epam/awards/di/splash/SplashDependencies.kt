package com.epam.awards.di.splash

import com.epam.awards.common.ComponentDependencies
import com.epam.awards.data.CredentialsStore

interface SplashDependencies : ComponentDependencies {
    fun credentialsStore(): CredentialsStore
}
