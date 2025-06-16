package com.target.targetcasestudy.ui.deal_detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.domain.usecase.RetrieveDeal
import com.target.targetcasestudy.ui.deal_detail.DealDetailUiState
import com.target.targetcasestudy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealDetailViewModel @Inject constructor(
    private val retrieveDeal: RetrieveDeal
) : ViewModel() {

    private val _uiState = MutableStateFlow<DealDetailUiState>(DealDetailUiState.InitialState)
    val uiState: StateFlow<DealDetailUiState> = _uiState

    /**
     * Fetch the deal by given ID
     */
    fun getDealById(id: Int) {
        viewModelScope.launch {
            _uiState.value = DealDetailUiState.Loading
            try {
                val deal = retrieveDeal(id)
                _uiState.value = DealDetailUiState.Success(deal)
            } catch (e: Exception) {
                Log.e("DealDetailViewModel", "Error fetching deal", e)
                _uiState.value = DealDetailUiState.Error(Constants.ERROR_TEXT_DEAL_DETAIL_SCREEN)
            }
        }
    }

    /**
     * Resets the UI state to [DealDetailUiState.InitialState].
     *
     * This function is useful when navigating away from the deal detail screen.
     * Calling it ensures that when the user returns, or if a new deal is subsequently
     * loaded, they don't briefly see stale data from a previous interaction.
     * It allows the UI to explicitly clear its content.
     */
    fun resetUiState() {
        _uiState.value = DealDetailUiState.InitialState
    }
}
