package com.epam.awards.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.epam.awards.R
import com.epam.awards.common.ViewBindingFragment
import com.epam.awards.common.findComponentDependencies
import com.epam.awards.data.CredentialsStore
import com.epam.awards.databinding.FragmentSplashBinding
import com.epam.awards.di.splash.DaggerSplashComponent
import javax.inject.Inject

class SplashFragment : ViewBindingFragment<FragmentSplashBinding>() {
    override val getBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    @Inject
    lateinit var credentialsStore: CredentialsStore

    override fun onAttach(context: Context) {
        performInject()
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navDest = if (credentialsStore.isLogged()) {
            R.id.action_splash_to_awards
        } else {
            R.id.action_splash_to_login
        }
        findNavController().navigate(navDest)
    }

    private fun performInject() {
        DaggerSplashComponent.builder()
            .splashDependencies(findComponentDependencies())
            .build()
            .inject(this)
    }
}
