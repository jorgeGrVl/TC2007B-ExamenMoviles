package com.app.examenmoviles.domain.usecase

import com.app.examenmoviles.domain.common.Result
import com.app.examenmoviles.domain.model.Sudoku
import com.app.examenmoviles.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSudokuUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        operator fun invoke(
            size: Int,
            difficulty: String,
        ): Flow<Result<Sudoku>> =
            flow {
                try {
                    emit(Result.Loading)

                    val sudoku =
                        repository.getSudoku(
                            size = size,
                            difficulty = difficulty,
                        )

                    emit(Result.Success(sudoku))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
