package com.match.statistics

import com.match.statistics.data.source.remote.lol.LoLDataSource
import com.match.statistics.di.NetworkModule
import com.match.statistics.util.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideLoLRetrofit(okHttpClient) }
    private val lolService by lazy { NetworkModule.provideLolService(retrofit) }
    private val loLDataSource = LoLDataSource(lolService)


    @Test
    fun test_load_user_profile() = runTest {
        val profile = loLDataSource.getSummonerProfile("genetory")
        Assert.assertTrue(profile is Resource.Success<*>)
    }

    @Test
    fun test_load_match_histories() = runTest {
        var match = loLDataSource.getSummonerMatches("genetory")
        Assert.assertTrue(match is Resource.Success<*>)

        for(i in 0 until 10) {
            val date = (match as Resource.Success).data.games.last().createDate
            match = loLDataSource.getSummonerMatches("genetory", date.toString())
            Assert.assertTrue(match is Resource.Success<*>)

            val dateList = (match as Resource.Success).data.games.map { it.createDate }
            Assert.assertFalse(dateList.contains(date))
        }
    }
}