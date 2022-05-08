package com.match.statistics.data.repository.lol

import com.match.statistics.data.source.remote.service.LoLService
import com.match.statistics.di.AppModule
import com.match.statistics.data.model.MatchesResponse
import com.match.statistics.data.model.PositionModel
import com.match.statistics.data.model.mapToDomain
import com.match.statistics.domain.model.lol.Analysis
import com.match.statistics.domain.model.lol.Champion
import com.match.statistics.domain.model.lol.Position
import com.match.statistics.util.wrapper.Resource
import com.match.statistics.domain.repository.lol.LoLStatisticsRepository
import com.match.statistics.util.modifyToValidUrl
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.net.ssl.SSLProtocolException
import kotlin.math.roundToInt

class LoLStatisticsRepositoryImpl @Inject constructor(
    private val loLService: LoLService,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): LoLStatisticsRepository {

    override suspend fun getMatches(userId:String, createDate:String?) = withContext(defaultDispatcher) {
        try {
            val response = loLService.getSummonerMatches(userId, createDate)
            return@withContext if (response.isSuccessful) {
                val res = response.body() as MatchesResponse
                Resource.Success(res.mapToDomain())
            } else {
                Resource.Failure(response.message())
            }
        } catch (e: SSLProtocolException) {
            e.printStackTrace()
            Resource.Failure("SSL handshake error.")
        }
    }

    override suspend fun getGameAnalysis(userId: String) = withContext(defaultDispatcher) {
        try {
            val response = loLService.getSummonerMatches(userId)
            return@withContext if(response.isSuccessful) {
                val res = response.body() as MatchesResponse

                var wins = 0
                var losses = 0
                var kills = 0f
                var deaths = 0f
                var assists = 0f
                var killContrib = 0
                var mostPosition = ""
                var positionWinRate = 0
                res.games.forEach {
                    if(it.isWin) wins++ else losses ++
                    kills += it.stats.general.kill
                    deaths += it.stats.general.death
                    assists += it.stats.general.assist
                    killContrib += it.stats.general.contributionForKillRate.filter { contrib -> contrib.isDigit() }.toInt()
                }

                res.positions.forEach {
                    if(it.wins.toFloat() > 0 && it.games.toFloat() > 0) {
                        val winRate = it.wins.toFloat() / it.games.toFloat() * 100
                        if(winRate > positionWinRate) {
                            positionWinRate = winRate.roundToInt()
                            mostPosition = it.position
                        }
                    }
                }

                Resource.Success(Analysis(
                    games = res.games.size,
                    wins = wins,
                    losses = losses,
                    kills = kills / res.games.size,
                    deaths = deaths / res.games.size,
                    assists = assists / res.games.size,
                    kda = (kills + assists) / deaths,
                    killContrib = killContrib / res.games.size,
                    mostChampions = res.champions.sortedByDescending { (it.wins.toFloat() / it.games.toFloat()) * 100 }.take(2).map {
                        Champion(
                            imageUrl = modifyToValidUrl(it.imageUrl),
                            winRate = (it.wins.toFloat() / it.games.toFloat() * 100).roundToInt()
                        )
                    },
                    mostPosition = mostPosition,
                    positionWinRate = positionWinRate
                ))
            }
            else {
                Resource.Failure(response.message())
            }
        } catch (e: SSLProtocolException) {
            e.printStackTrace()
            Resource.Failure("SSL handshake error.")
        }
    }
}