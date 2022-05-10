package com.match.statistics.data.source.remote.service

import com.match.statistics.data.model.MatchesResponse
import com.match.statistics.data.model.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Retrofit 서비스
 */
interface LoLService {

    companion object {
        const val BASE_URL_V1 = "https://codingtest.op.gg/api/"
        const val MATCH_PAGE_SIZE=20
    }

    @GET("summoner/{id}")
    suspend fun getSummonerProfile(
        @Path("id", encoded = true) id: String
    ) : Response<SummonerResponse>

    @GET("summoner/{id}/matches")
    suspend fun getSummonerMatches(
        @Path("id", encoded = true) id: String,
        @Query("lastMatch") createDate: String? = null
    ) : Response<MatchesResponse>
}