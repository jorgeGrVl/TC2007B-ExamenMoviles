package com.app.examenmoviles.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmoviles.data.local.preferences.SudokuPreferences
import com.app.examenmoviles.domain.usecase.DeleteSavedGameUseCase
import com.app.examenmoviles.domain.usecase.LoadSavedGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val loadSavedGameUseCase: LoadSavedGameUseCase,
        private val deleteSavedGameUseCase: DeleteSavedGameUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState: StateFlow<HomeUiState> = _uiState

        init {
            loadSavedGameState()
        }

        private fun loadSavedGameState() {
            viewModelScope.launch {
                val saved = loadSavedGameUseCase()
                _uiState.value = _uiState.value.copy(hasSavedGame = saved != null)
            }
        }

        fun deleteSavedGame() {
            viewModelScope.launch {
                deleteSavedGameUseCase()
                _uiState.value = _uiState.value.copy(hasSavedGame = false)
            }
        }

        fun continueSavedGame(onContinue: () -> Unit) {
            onContinue()
        }

        fun updateSize(size: Int) {
            _uiState.value = _uiState.value.copy(selectedSize = size)
        }

        fun updateDifficulty(difficulty: String) {
            _uiState.value = _uiState.value.copy(selectedDifficulty = difficulty)
        }

        fun onGenerateSudoku(onSuccess: (Int, String) -> Unit) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)

                onSuccess(_uiState.value.selectedSize, _uiState.value.selectedDifficulty)

                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
