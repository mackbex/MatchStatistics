package com.match.statistics.domain.usecase

import com.match.statistics.domain.model.lol.Summoner
import com.match.statistics.domain.model.lol.SummonerInfo
import com.match.statistics.domain.repository.lol.LoLStatisticsRepository
import com.match.statistics.domain.repository.lol.LoLUserRepository
import com.match.statistics.util.wrapper.Resource
import javax.inject.Inject


class UserInfoUseCase @Inject constructor(
    private val loLUserRepository: LoLUserRepository,
    private val loLStatisticsRepository: LoLStatisticsRepository
) {
    suspend fun getLolSummonerName() = loLUserRepository.getUserId()
    suspend fun getLoLSummonerInfo(userId:String):Resource<SummonerInfo> {

        val summoner = loLUserRepository.getSummonerInfo(userId)
        val analysis = loLStatisticsRepository.getGameAnalysis(userId)

        return when(summoner) {
            is Resource.Success -> {
                Resource.Success(SummonerInfo(
                    summoner.data,
                    analysis = if(analysis is Resource.Success) analysis.data else null,
                ))
            }
            else -> {
                Resource.Failure("No search result.")
            }
        }
    }
    suspend fun getLoLMatches(userId:String, createDate:String?) = loLStatisticsRepository.getMatches(userId, createDate)
}