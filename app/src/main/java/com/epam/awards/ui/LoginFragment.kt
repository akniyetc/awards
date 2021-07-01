package com.epam.awards.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.epam.awards.R
import com.epam.awards.common.ViewBindingFragment
import com.epam.awards.common.ViewModelFactory
import com.epam.awards.common.findComponentDependencies
import com.epam.awards.common.getViewModel
import com.epam.awards.common.visible
import com.epam.awards.databinding.FragmentLoginBinding
import com.epam.awards.di.login.DaggerLoginComponent
import com.epam.awards.presentation.login.InputErrorType
import com.epam.awards.presentation.login.LoginViewModel
import com.epam.awards.presentation.login.LoginViewState
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {
    override val getBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    @Inject
    lateinit var factory: ViewModelFactory<LoginViewModel>

    private val viewModel: LoginViewModel by lazy {
        getViewModel(LoginViewModel::class.java, factory)
    }

    override fun onAttach(context: Context) {
        performInject()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)

        binding.submitButton.setOnClickListener {
            val userName = binding.userNameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            viewModel.login(userName = userName, password = password)
        }
    }

    private fun performInject() {
        DaggerLoginComponent.factory().create(findComponentDependencies()).inject(this)
    }

    private fun handleViewState(viewState: LoginViewState) {
        when (viewState) {
            LoginViewState.Loading -> binding.progress.visible(true)
            LoginViewState.Error -> {
                binding.progress.visible(false)
                showMessage(R.string.general_error)
            }
            is LoginViewState.Signed -> {
                binding.progress.visible(false)
                findNavController().navigate(R.id.action_login_to_awards)
            }
            is LoginViewState.InputError -> {
                binding.progress.visible(false)
                when (viewState.type) {
                    InputErrorType.WRONG_USER_NAME -> {
                        binding.passwordInputLayout.error = null
                        binding.userNameInputLayout.error =
                            resources.getString(R.string.input_user_name_wrong)
                    }
                    InputErrorType.WRONG_PASSWORD -> {
                        binding.userNameInputLayout.error = null
                        binding.passwordInputLayout.error =
                            resources.getString(R.string.input_password_wrong)
                    }
                }
            }
        }
    }

    private fun showMessage(@StringRes stringId: Int) {
        Snackbar.make(binding.root, stringId, Snackbar.LENGTH_SHORT).show()
    }
}
