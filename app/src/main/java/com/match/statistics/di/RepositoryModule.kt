package com.match.statistics.di

import com.match.statistics.data.repository.lol.LoLStatisticsRepositoryImpl
import com.match.statistics.data.repository.lol.LoLUserRepositoryImpl
import com.match.statistics.domain.repository.lol.LoLStatisticsRepository
import com.match.statistics.domain.repository.lol.LoLUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindStatisticsRepository(impl: LoLStatisticsRepositoryImpl): LoLStatisticsRepository

    @Binds
    fun bindUserRepository(impl: LoLUserRepositoryImpl): LoLUserRepository
}