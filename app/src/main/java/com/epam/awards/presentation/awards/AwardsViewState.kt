package com.epam.awards.presentation.awards

sealed class AwardsViewState {
    data class Success(val awards: List<AwardView>): AwardsViewState()
    object Loading: AwardsViewState()
    object Error: AwardsViewState()
}

data class AwardView(
    val nominatorFullName: String,
    val nomineeFullName: String,
    val title: String,
    val nomineeId: Int
)
