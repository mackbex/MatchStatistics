package com.match.statistics.domain.repository.lol

import com.match.statistics.util.wrapper.Resource
import com.match.statistics.domain.model.lol.Summoner

interface LoLUserRepository {
    suspend fun getSummonerInfo(summonerName:String) : Resource<Summoner>
    suspend fun getSummonerName() : Resource<String>
}