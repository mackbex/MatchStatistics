package com.match.statistics.data.repository.lol

import android.content.Context
import com.match.statistics.data.source.remote.service.LoLService
import com.match.statistics.di.AppModule
import com.match.statistics.data.model.SummonerResponse
import com.match.statistics.data.model.mapToDomain
import com.match.statistics.domain.repository.lol.LoLUserRepository
import com.match.statistics.util.Constants.SHARED_PREFERENCE_USER_ID
import com.match.statistics.util.Constants.SHARED_PREFERENCE_USER_INFO
import com.match.statistics.util.wrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.net.ssl.SSLProtocolException

class LoLUserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context:Context,
    private val loLService: LoLService,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): LoLUserRepository {

    override suspend fun getSummonerInfo(userId:String) = withContext(defaultDispatcher) {

        try {
            val response = loLService.getSummonerProfile(userId)
            return@withContext if (response.isSuccessful) {
                val res = (response.body() as SummonerResponse)
                res.summoner.leagues.map {
                    it.winRate = (it.wins.toFloat() / (it.wins + it.losses) * 100).toInt()
                }
                Resource.Success(res.mapToDomain())
            } else {
                Resource.Failure(response.message())
            }
        }
        catch (e:SSLProtocolException) {
            e.printStackTrace()
            Resource.Failure("SSL handshake error.")
        }
    }

    /**
     * prepare Local dao
     */
    override suspend fun getUserId() = withContext(defaultDispatcher) {
        val storedId = context.getSharedPreferences(SHARED_PREFERENCE_USER_INFO, Context.MODE_PRIVATE).getString(SHARED_PREFERENCE_USER_ID, null)
        Resource.Success(if(storedId.isNullOrEmpty()) { "genetory" } else { storedId })
    }
}