package com.target.targetcasestudy.ui.deal_detail

import com.target.targetcasestudy.data.model.Deal

sealed class DealDetailUiState {
    object InitialState : DealDetailUiState()
    object Loading : DealDetailUiState()
    data class Success(val deal: Deal) : DealDetailUiState()
    data class Error(val message: String) : DealDetailUiState()
}