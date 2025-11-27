package com.app.examenmoviles.data.mapper

import com.app.examenmoviles.data.remote.dto.SudokuDto
import com.app.examenmoviles.domain.model.Sudoku

fun SudokuDto.toDomain(size: Int): Sudoku =
    Sudoku(
        puzzle = puzzle,
        solution = solution,
        size = size,
    )
