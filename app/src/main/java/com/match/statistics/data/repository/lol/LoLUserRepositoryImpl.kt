package com.match.statistics.data.repository.lol

import android.content.Context
import com.match.statistics.di.AppModule
import com.match.statistics.data.model.mapToDomain
import com.match.statistics.data.source.remote.lol.LoLDataSource
import com.match.statistics.domain.repository.lol.LoLUserRepository
import com.match.statistics.util.Constants.SHARED_PREFERENCE_USER_ID
import com.match.statistics.util.Constants.SHARED_PREFERENCE_USER_INFO
import com.match.statistics.util.wrapper.Resource
import com.match.statistics.util.wrapper.map
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoLUserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context:Context,
    private val loLDataSource: LoLDataSource,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): LoLUserRepository {

    /**
     * 롤 계정 정보 조회
     */
    override suspend fun getSummonerInfo(summonerName:String) = withContext(defaultDispatcher) {
        val res = loLDataSource.getSummonerProfile(summonerName)
        return@withContext res.map {
            it.mapToDomain().apply {
                this.leagues.map {
                    it.winRate = (it.wins.toFloat() / (it.wins + it.losses) * 100).toInt()
                }
            }
        }
    }

    /**
     * 유저 아이디 가져옴(prepare Local dao)
     * 테스트용으로 mainActivity에서 인포 저장
     */
    override suspend fun getSummonerName() = withContext(defaultDispatcher) {
        val storedId = context.getSharedPreferences(SHARED_PREFERENCE_USER_INFO, Context.MODE_PRIVATE).getString(SHARED_PREFERENCE_USER_ID, null)
        Resource.Success(if(storedId.isNullOrEmpty()) { "genetory" } else { storedId })
    }
}