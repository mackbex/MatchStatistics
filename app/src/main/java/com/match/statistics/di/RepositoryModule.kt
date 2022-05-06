package com.match.statistics.di

import com.match.statistics.data.repository.CompanyInfoRepositoryImpl
import com.match.statistics.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {


    @Binds
    fun bindCompanyRepository(impl : CompanyInfoRepositoryImpl): CompanyRepository
}