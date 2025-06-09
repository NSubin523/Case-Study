package com.target.targetcasestudy.ui.deal_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.domain.usecase.RetrieveDeals
import com.target.targetcasestudy.ui.deal_list.DealListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealListViewModel @Inject constructor(
    private val dealUseCase: RetrieveDeals
) : ViewModel() {

    private val _uiState = MutableStateFlow<DealListUiState>(DealListUiState.Loading)
    val uiState: StateFlow<DealListUiState> = _uiState

    init {
        getDeals()
    }

    private fun getDeals() {
        viewModelScope.launch {
            try {
                val deals = dealUseCase.invoke()
                _uiState.value = DealListUiState.Success(deals)
            } catch (e: Exception) {
                _uiState.value = DealListUiState.Error("Failed to load deals")
            }
        }
    }
}

