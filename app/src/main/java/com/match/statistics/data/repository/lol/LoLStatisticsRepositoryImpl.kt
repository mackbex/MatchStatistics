package com.match.statistics.data.repository.lol

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.match.statistics.di.AppModule
import com.match.statistics.data.source.remote.lol.LoLDataSource
import com.match.statistics.data.source.remote.lol.MatchPagingSource
import com.match.statistics.data.source.remote.service.LoLService.Companion.MATCH_PAGE_SIZE
import com.match.statistics.domain.model.lol.Analysis
import com.match.statistics.domain.model.lol.Champion
import com.match.statistics.domain.model.lol.Match
import com.match.statistics.util.wrapper.Resource
import com.match.statistics.domain.repository.lol.LoLStatisticsRepository
import com.match.statistics.util.modifyToValidUrl
import com.match.statistics.util.wrapper.map
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.roundToInt

class LoLStatisticsRepositoryImpl @Inject constructor(
    private val loLRemoteDataSource: LoLDataSource,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
) : LoLStatisticsRepository {

    /**
     * 매치 히스토리 조회
     */
    override fun getMatches(summonerName: String, createDate: String?): Flow<PagingData<Match>> {
        return Pager(
            config = PagingConfig(
                pageSize = MATCH_PAGE_SIZE
            ),
            pagingSourceFactory = {
                MatchPagingSource(
                    loLRemoteDataSource,
                    summonerName,
                    defaultDispatcher
                )
            }
        ).flow
    }

    /**
     * 최근 N게임 분석
     */
    override suspend fun getGameAnalysis(summonerName: String) = withContext(defaultDispatcher) {
        val res = loLRemoteDataSource.getSummonerMatches(summonerName)
        return@withContext res.map { data ->
            var wins = 0
            var losses = 0
            var kills = 0f
            var deaths = 0f
            var assists = 0f
            var killContrib = 0
            var mostPosition = ""
            var positionWinRate = 0
            val listSize = data.games.size
            data.games.forEach {
                if (it.isWin) wins++ else losses++
                kills += it.stats.general.kill
                deaths += it.stats.general.death
                assists += it.stats.general.assist
                killContrib += it.stats.general.contributionForKillRate.filter { contrib -> contrib.isDigit() }
                    .toInt()
            }

            data.positions.forEach {
                if (it.wins.toFloat() > 0 && it.games.toFloat() > 0) {
                    val winRate = it.wins.toFloat() / it.games.toFloat() * 100
                    if (winRate > positionWinRate) {
                        positionWinRate = winRate.roundToInt()
                        mostPosition = it.position
                    }
                }
            }

            Analysis(
                games = listSize,
                wins = wins,
                losses = losses,
                kills = kills / listSize,
                deaths = deaths / listSize,
                assists = assists / listSize,
                kda = (kills + assists) / deaths,
                killContrib = killContrib / listSize,
                mostChampions = data.champions.sortedByDescending { (it.wins.toFloat() / it.games.toFloat()) * 100 }
                    .take(2).map {
                        Champion(
                            imageUrl = modifyToValidUrl(it.imageUrl),
                            winRate = (it.wins.toFloat() / it.games.toFloat() * 100).roundToInt()
                        )
                    },
                mostPosition = mostPosition,
                positionWinRate = positionWinRate
            )
        }
    }
}