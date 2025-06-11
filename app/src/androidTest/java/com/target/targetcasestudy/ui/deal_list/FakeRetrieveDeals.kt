package com.target.targetcasestudy.ui.deal_list

import com.target.targetcasestudy.data.model.Deal

class FakeRetrieveDeals(private val deals: List<Deal>) {
    suspend operator fun invoke(): List<Deal> = deals
}