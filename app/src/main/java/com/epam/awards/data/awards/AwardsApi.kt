package com.epam.awards.data.awards

import com.epam.awards.domain.entity.Award
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header

private const val LANG = "en-us"
private const val PAGE_NUM = "1"
private const val PAGE_SIZE = "20"

interface AwardsApi {

    @GET("ws/v3/awards/awardsfeed")
    fun award(
        @Header("lang") lang: String = LANG,
        @Header("pageNum") page: String = PAGE_NUM,
        @Header("pageSize") pageSize: String = PAGE_SIZE,
    ) : Single<Award>
}
