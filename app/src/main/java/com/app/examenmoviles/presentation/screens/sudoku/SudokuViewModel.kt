package com.app.examenmoviles.presentation.screens.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SudokuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SudokuUiState())
    val uiState: StateFlow<SudokuUiState> = _uiState

    fun initialize(
        size: Int,
        difficulty: String,
    ) {
        viewModelScope.launch {
            val generatedBoard =
                List(size) { row ->
                    List(size) { col ->
                        if ((row + col) % 3 == 0) null else ((row * col) % size) + 1
                    }
                }

            _uiState.value =
                SudokuUiState(
                    size = size,
                    difficulty = difficulty,
                    board = generatedBoard,
                    initialBoard = generatedBoard, // 👈 ESTA ES LA CLAVE
                    isLoading = false,
                )
        }
    }

    fun updateCell(
        row: Int,
        col: Int,
        value: Int?,
    ) {
        val current =
            _uiState.value.board
                .map { it.toMutableList() }
                .toMutableList()
        current[row][col] = value

        _uiState.value = _uiState.value.copy(board = current)
    }
}
