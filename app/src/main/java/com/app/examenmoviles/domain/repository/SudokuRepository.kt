package com.app.examenmoviles.domain.repository

import com.app.examenmoviles.domain.model.Sudoku
import com.app.examenmoviles.domain.model.SudokuStatus

interface SudokuRepository {
    suspend fun getSudoku(
        size: Int,
        difficulty: String,
    ): Sudoku

    suspend fun checkStatus(
        puzzle: List<List<Int>>,
        width: Int,
        height: Int,
    ): SudokuStatus
}
