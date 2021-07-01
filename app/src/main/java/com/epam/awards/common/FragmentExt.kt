package com.epam.awards.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory

fun <T : ViewModel> Fragment.getViewModel(
    modelClass: Class<T>,
    viewModelFactory: Factory? = null
): T {
    val viewModelProvider = viewModelFactory?.let {
        ViewModelProvider(this, it)
    } ?: ViewModelProvider(this)
    return viewModelProvider.get(modelClass)
}

fun <T : ViewModel> FragmentActivity.getViewModel(
    modelClass: Class<T>,
    viewModelFactory: Factory? = null
): T {
    val viewModelProvider = viewModelFactory?.let {
        ViewModelProvider(this, it)
    } ?: ViewModelProvider(this)
    return viewModelProvider.get(modelClass)
}
