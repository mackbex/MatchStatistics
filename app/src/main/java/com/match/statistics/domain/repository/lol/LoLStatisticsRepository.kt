package com.match.statistics.domain.repository.lol

import androidx.paging.PagingData
import com.match.statistics.data.model.GameModel
import com.match.statistics.domain.model.lol.Analysis
import com.match.statistics.domain.model.lol.Match
import com.match.statistics.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface LoLStatisticsRepository {
    fun getMatches(summonerName:String, createDate:String? = null) : Flow<PagingData<Match>>
    suspend fun getGameAnalysis(summonerName:String) : Resource<Analysis>
}