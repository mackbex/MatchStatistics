package com.match.statistics.data.source.remote.lol

import com.match.statistics.data.source.remote.BaseDataSource
import com.match.statistics.data.source.remote.service.LoLService
import javax.inject.Inject

/**
 * Retrofit 데이터소스
 */
class LoLDataSource @Inject constructor(
    private val loLService: LoLService
):BaseDataSource(){

    /**
     * 롤 계정 정보 조회
     */
    suspend fun getSummonerProfile(id:String) = getResult { loLService.getSummonerProfile(id) }
    /**
     * 매치 히스토리 조회
     */
    suspend fun getSummonerMatches(id:String, createData:String? = null) = getResult { loLService.getSummonerMatches(id, createData) }
}