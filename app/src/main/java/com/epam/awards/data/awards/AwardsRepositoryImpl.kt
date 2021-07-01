package com.epam.awards.data.awards

import com.epam.awards.domain.entity.SocialAward
import com.epam.awards.domain.repo.AwardsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AwardsRepositoryImpl @Inject constructor(
    private val awardsApi: AwardsApi
) : AwardsRepository {

    override fun awards(): Single<List<SocialAward>> =
        awardsApi.award()
            .map { award -> award.socialAward }
}
