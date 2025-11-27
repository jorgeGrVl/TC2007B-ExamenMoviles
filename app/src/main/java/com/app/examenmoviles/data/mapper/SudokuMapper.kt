package com.app.examenmoviles.data.mapper

import com.app.examenmoviles.data.remote.dto.SudokuDto
import com.app.examenmoviles.data.remote.dto.SudokuStatusDto
import com.app.examenmoviles.domain.model.Sudoku
import com.app.examenmoviles.domain.model.SudokuStatus

fun SudokuDto.toDomain(size: Int): Sudoku =
    Sudoku(
        puzzle = puzzle,
        solution = solution,
        size = size,
    )

fun SudokuStatusDto.toDomain(): SudokuStatus =
    SudokuStatus(
        status = this.status,
        solution = this.solution,
    )
