package com.app.examenmoviles.presentation.screens.sudoku.components

sealed class SudokuError {
    object NoConnection : SudokuError()

    object InvalidResponse : SudokuError()

    object ServerError : SudokuError()

    object InvalidLocalData : SudokuError()

    data class Unknown(
        val message: String? = null,
    ) : SudokuError()
}
