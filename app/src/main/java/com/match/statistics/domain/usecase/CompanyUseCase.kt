package com.match.statistics.domain.usecase

import com.match.statistics.domain.repository.CompanyRepository
import javax.inject.Inject


class CompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend fun getSearchResult() = companyRepository.getCompanyList()
}