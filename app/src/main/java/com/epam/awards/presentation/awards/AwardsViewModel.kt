package com.epam.awards.presentation.awards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.epam.awards.common.BaseViewModel
import com.epam.awards.common.SchedulersProvider
import com.epam.awards.domain.AwardsUseCase
import javax.inject.Inject

class AwardsViewModel @Inject constructor(
    private val awardsUseCase: AwardsUseCase,
    private val schedulers: SchedulersProvider,
    private val mapper: AwardsMapper
) : BaseViewModel() {

    private val _awardsViewState = MutableLiveData<AwardsViewState>()
    val awardsViewState: LiveData<AwardsViewState> get() = _awardsViewState

    init {
        loadAwards()
    }

    private fun loadAwards() {
        awardsUseCase.awards()
            .map { socialAward -> mapper.mapToAwardView(socialAward) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .doOnSubscribe { _awardsViewState.value = AwardsViewState.Loading }
            .subscribe(
                { _awardsViewState.value = AwardsViewState.Success(it) },
                { _awardsViewState.value = AwardsViewState.Error }
            )
            .selfDispose()
    }
}
