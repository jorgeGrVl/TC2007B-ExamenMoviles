package com.app.examenmoviles.data.repository

import com.app.examenmoviles.data.mapper.toDomain
import com.app.examenmoviles.data.remote.api.SudokuApi
import com.app.examenmoviles.domain.model.Sudoku
import com.app.examenmoviles.domain.repository.SudokuRepository
import javax.inject.Inject

class SudokuRepositoryImpl
    @Inject
    constructor(
        private val api: SudokuApi,
    ) : SudokuRepository {
        override suspend fun getSudoku(
            size: Int,
            difficulty: String,
        ): Sudoku {
            // Mapeo del size del sudoku a ancho y alto de caja
            val boxSize =
                when (size) {
                    9 -> 3 // 3x3 -> sudoku de 9
                    4 -> 2
                    else -> throw IllegalArgumentException("Sudoku size no soportado: $size")
                }

            val dto =
                api.getSudoku(
                    apiKey = "a5NKA4ikuCraD3WlAuQGVw==hOSJsp9ZdzkjLpax",
                    width = boxSize,
                    height = boxSize,
                    difficulty = difficulty,
                )

            return dto.toDomain(size)
        }
    }
