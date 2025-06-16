package com.target.targetcasestudy.ui.deal_list

import com.target.targetcasestudy.data.model.Deal

sealed class DealListUiState {
    object Loading : DealListUiState()
    data class Success(val deals: List<Deal>) : DealListUiState()
    data class Error(val message: String) : DealListUiState()
}
