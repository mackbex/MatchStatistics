package com.match.statistics.data.source.remote.lol

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.match.statistics.data.model.mapToDomain
import com.match.statistics.di.AppModule
import com.match.statistics.domain.model.lol.Match
import com.match.statistics.util.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import javax.inject.Inject

class MatchPagingSource @Inject constructor(
    private val dataSource: LoLDataSource,
    private val summonerName:String,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): PagingSource<String, Match>() {

    override fun getRefreshKey(state: PagingState<String, Match>): String? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.createDate.toString()
        }
    }

    override suspend fun load(params: LoadParams<String>) = withContext(defaultDispatcher) {
        val page = params.key
        return@withContext try {
            when(val matches = dataSource.getSummonerMatches(summonerName, page)) {
                is Resource.Success -> {
                    LoadResult.Page(
                        data = matches.data.mapToDomain(),
                        prevKey = null,
                        nextKey = matches.data.games.last().createDate.toString()
                    )
                }
                else -> {
                    LoadResult.Error(IllegalStateException())
                }
            }
        } catch (e: Exception) {
            return@withContext LoadResult.Error(e)
        }
    }
}