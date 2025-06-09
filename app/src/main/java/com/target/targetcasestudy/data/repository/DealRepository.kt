package com.target.targetcasestudy.data.repository

import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.data.api.DealApiKtx
import javax.inject.Inject

class DealRepository @Inject constructor(
    private val api: DealApiKtx
) {
    suspend fun retrieveDeals(): List<Deal> = api.retrieveDeals().deals

    suspend fun retrieveDeal(id: String): Deal = api.retrieveDeal(id)
}
