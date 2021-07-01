package com.epam.awards.presentation.awards

import com.epam.awards.domain.entity.SocialAward
import javax.inject.Inject

class AwardsMapper @Inject constructor() {

    fun mapToAwardView(awards: List<SocialAward>): List<AwardView> =
        awards.map { socialAward ->
            mapAward(socialAward)
        }

    private fun mapAward(socialAward: SocialAward): AwardView =
        AwardView(
            title = socialAward.awardTitle,
            nominatorFullName = "${socialAward.nominatorFirstName} ${socialAward.nominatorLastName}",
            nomineeFullName = "${socialAward.nomineeFirstName} ${socialAward.nomineeLastName}",
            nomineeId = socialAward.nomineeId
        )
}
