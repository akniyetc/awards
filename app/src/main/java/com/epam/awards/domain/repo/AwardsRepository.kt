package com.epam.awards.domain.repo

import com.epam.awards.domain.entity.SocialAward
import io.reactivex.rxjava3.core.Single

interface AwardsRepository {
    fun awards(): Single<List<SocialAward>>
}
