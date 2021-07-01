package com.epam.awards.domain

import com.epam.awards.domain.repo.AwardsRepository
import javax.inject.Inject

class AwardsUseCase @Inject constructor(
    private val awardsRepository: AwardsRepository
) {

    fun awards() = awardsRepository.awards()
}
