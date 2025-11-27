package com.app.examenmoviles.presentation.screens.sudoku

data class SudokuUiState(
    val size: Int = 0,
    val difficulty: String = "",
    val board: List<List<Int?>> = emptyList(),
    val initialBoard: List<List<Int?>> = emptyList(), // 👈 NUEVO
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSolved: Boolean? = null,
)
