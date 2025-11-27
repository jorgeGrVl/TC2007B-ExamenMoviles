package com.app.examenmoviles.domain.repository

import com.app.examenmoviles.domain.model.Sudoku

interface SudokuRepository {
    suspend fun getSudoku(
        size: Int,
        difficulty: String,
    ): Sudoku
}
