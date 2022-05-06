package com.match.statistics.data.source.remote.service

import com.match.statistics.domain.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET

/**
 * 기업정보 검색 서비스
 */
interface CompanySearchService {
    @GET("mobile-config/test_data.json")
    suspend fun getCompanyList() : Response<SearchResult>
}