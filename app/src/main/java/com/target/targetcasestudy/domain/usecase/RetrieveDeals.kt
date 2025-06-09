package com.target.targetcasestudy.domain.usecase

import com.target.targetcasestudy.data.repository.DealRepository
import javax.inject.Inject

class RetrieveDeals @Inject constructor(
    private val repository: DealRepository
) {
    suspend operator fun invoke() = repository.retrieveDeals()
}
