package com.target.targetcasestudy.domain.usecase

import com.target.targetcasestudy.data.repository.DealRepository
import javax.inject.Inject

class RetrieveDeal @Inject constructor(
    private val repository: DealRepository
) {
    suspend operator fun invoke(id: Int) = repository.retrieveDeal(id)
}
