package com.app.examenmoviles.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun updateSize(size: Int) {
        _uiState.value = _uiState.value.copy(selectedSize = size)
    }

    fun updateDifficulty(difficulty: String) {
        _uiState.value = _uiState.value.copy(selectedDifficulty = difficulty)
    }

    fun onGenerateSudoku(onSuccess: (Int, String) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Aún no hay llamadas al API, simplemente simula éxito.
            onSuccess(_uiState.value.selectedSize, _uiState.value.selectedDifficulty)

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
