package com.app.examenmoviles.presentation.screens.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmoviles.domain.common.Result
import com.app.examenmoviles.domain.usecase.GetSudokuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel
    @Inject
    constructor(
        private val getSudokuUseCase: GetSudokuUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SudokuUiState())
        val uiState: StateFlow<SudokuUiState> = _uiState

        fun initialize(
            size: Int,
            difficulty: String,
        ) {
            viewModelScope.launch {
                getSudokuUseCase(size, difficulty).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = true,
                                )
                        }

                        is Result.Success -> {
                            val sudoku = result.data

                            _uiState.value =
                                SudokuUiState(
                                    size = size,
                                    difficulty = difficulty,
                                    board = sudoku.puzzle,
                                    initialBoard = sudoku.puzzle,
                                    isLoading = false,
                                )
                        }

                        is Result.Error -> {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    error = result.exception.message ?: "Error desconocido",
                                )
                        }
                    }
                }
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
