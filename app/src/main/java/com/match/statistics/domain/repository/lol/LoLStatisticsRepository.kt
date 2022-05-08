package com.match.statistics.domain.repository.lol

import com.match.statistics.domain.model.lol.Analysis
import com.match.statistics.domain.model.lol.Match
import com.match.statistics.util.wrapper.Resource

interface LoLStatisticsRepository {
    suspend fun getMatches(userId:String, createDate:String? = null) : Resource<List<Match>>
    suspend fun getGameAnalysis(userId:String) : Resource<Analysis>
}