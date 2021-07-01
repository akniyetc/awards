package com.epam.awards.domain.entity

import androidx.annotation.Keep

@Keep
data class Award(
	val pageSize: Int,
	val socialAward: List<SocialAward>,
	val pageNum: Int
)

@Keep
data class SocialAward(
	val nominatorLastName: String,
	val nomineeLastName: String,
	val latestBravoDate: String,
	val productId: Int,
	val nominatorId: Int,
	val nominatorFirstName: String,
	val nomineeFirstName: String,
	val awardReasonId: Int,
	val privacy: String,
	val awardTitle: String,
	val awardDate: String,
	val nominatorPicture: String,
	val nomineePicture: String,
	val nomineeId: Int,
	val awardReason: String,
	val specialWording: String,
	val id: String,
	val events: List<Any>,
	val nominationId: Int
)

