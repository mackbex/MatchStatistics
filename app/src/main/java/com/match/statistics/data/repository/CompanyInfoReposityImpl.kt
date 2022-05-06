package com.match.statistics.data.repository

import com.match.statistics.data.source.remote.service.CompanySearchService
import com.match.statistics.di.AppModule
import com.match.statistics.util.Resource
import com.match.statistics.domain.model.*
import com.match.statistics.domain.repository.CompanyRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class CompanyInfoRepositoryImpl @Inject constructor(
    private val companySearchService: CompanySearchService,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): CompanyRepository {

    /**
     * 서버로부터 기업정보 리스트 받아옴
     */
    override suspend fun getCompanyList() = withContext(defaultDispatcher) {
        val response = companySearchService.getCompanyList()
        val result = if(response.isSuccessful) {
            val list = response.body()
            Resource.Success<SearchResult>(list ?: run {SearchResult()})
        }
        else {
            Resource.Failure(response.message())
        }
        result
    }
}