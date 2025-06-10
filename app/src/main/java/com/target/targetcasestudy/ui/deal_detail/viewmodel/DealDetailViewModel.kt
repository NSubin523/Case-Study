package com.target.targetcasestudy.ui.deal_detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.domain.usecase.RetrieveDeal
import com.target.targetcasestudy.ui.deal_detail.DealDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealDetailViewModel @Inject constructor(
    private val retrieveDeal: RetrieveDeal
) : ViewModel() {

    private val _uiState = MutableStateFlow<DealDetailUiState>(DealDetailUiState.Loading)
    val uiState: StateFlow<DealDetailUiState> = _uiState

    /**
     * Fetch the deal by given ID
     */
    fun getDealById(id: Int) {
        viewModelScope.launch {
            try {
                val deal = retrieveDeal(id)
                _uiState.value = DealDetailUiState.Success(deal)
            } catch (e: Exception) {
                Log.e("DealDetailViewModel", "Error fetching deal", e)
                _uiState.value = DealDetailUiState.Error("Failed to load deal details")
            }
        }
    }
}
