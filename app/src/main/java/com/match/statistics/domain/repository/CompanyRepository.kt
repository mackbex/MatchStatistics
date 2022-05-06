package com.match.statistics.domain.repository

import com.match.statistics.util.Resource
import com.match.statistics.domain.model.SearchResult

interface CompanyRepository {
    suspend fun getCompanyList() : Resource<SearchResult>
}