package com.epam.awards.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.epam.awards.R
import com.epam.awards.common.ViewBindingFragment
import com.epam.awards.common.ViewModelFactory
import com.epam.awards.common.findComponentDependencies
import com.epam.awards.common.getViewModel
import com.epam.awards.common.visible
import com.epam.awards.databinding.FragmentAwardsBinding
import com.epam.awards.di.awards.DaggerAwardsComponent
import com.epam.awards.presentation.awards.AwardsViewModel
import com.epam.awards.presentation.awards.AwardsViewState
import com.epam.awards.presentation.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AwardsFragment : ViewBindingFragment<FragmentAwardsBinding>() {
    override val getBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAwardsBinding
        get() = FragmentAwardsBinding::inflate

    @Inject
    lateinit var factory: ViewModelFactory<AwardsViewModel>

    private val viewModel: AwardsViewModel by lazy {
        getViewModel(AwardsViewModel::class.java, factory)
    }

    private val adapter = AwardsAdapter()

    override fun onAttach(context: Context) {
        performInject()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.awardsViewState.observe(viewLifecycleOwner, ::handleAwardsState)
        binding.awardsList.adapter = adapter
    }

    private fun handleAwardsState(awardViewState: AwardsViewState) {
        when (awardViewState) {
            AwardsViewState.Loading -> binding.progress.visible(true)
            AwardsViewState.Error -> {
                binding.progress.visible(false)
                showMessage(R.string.general_error)
            }
            is AwardsViewState.Success -> {
                binding.progress.visible(false)
                adapter.submitList(awardViewState.awards)
            }
        }
    }

    private fun performInject() {
        DaggerAwardsComponent.builder()
            .awardsDependencies(findComponentDependencies())
            .build()
            .inject(this)
    }

    private fun showMessage(@StringRes stringId: Int) {
        Snackbar.make(binding.root, stringId, Snackbar.LENGTH_SHORT).show()
    }
}
