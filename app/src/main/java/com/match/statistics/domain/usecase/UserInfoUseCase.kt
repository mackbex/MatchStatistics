package com.match.statistics.domain.usecase

import com.match.statistics.domain.model.lol.SummonerInfo
import com.match.statistics.domain.repository.lol.LoLStatisticsRepository
import com.match.statistics.domain.repository.lol.LoLUserRepository
import com.match.statistics.util.wrapper.Resource
import javax.inject.Inject


class UserInfoUseCase @Inject constructor(
    private val loLUserRepository: LoLUserRepository,
    private val loLStatisticsRepository: LoLStatisticsRepository
) {
    /**
     * 롤 아이디 조회(로그인 시 사용)
     */
    suspend fun getLolSummonerName() = loLUserRepository.getSummonerName()

    /**
     * 롤 게임 정보 조회
     */
    suspend fun getLoLSummonerInfo(summonerName:String):Resource<SummonerInfo> {

        val summoner = loLUserRepository.getSummonerInfo(summonerName)
        val analysis = loLStatisticsRepository.getGameAnalysis(summonerName)

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
    fun getLoLMatches(userId:String, createDate:String?) = loLStatisticsRepository.getMatches(userId, createDate)
}