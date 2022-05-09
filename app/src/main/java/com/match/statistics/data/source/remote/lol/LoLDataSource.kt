package com.match.statistics.data.source.remote.lol

import com.match.statistics.data.source.remote.BaseDataSource
import com.match.statistics.data.source.remote.service.LoLService
import javax.inject.Inject

class LoLDataSource @Inject constructor(
    private val loLService: LoLService
):BaseDataSource(){
    suspend fun getSummonerProfile(id:String) = getResult { loLService.getSummonerProfile(id) }
    suspend fun getSummonerMatches(id:String, createData:String? = null) = getResult { loLService.getSummonerMatches(id, createData) }
}