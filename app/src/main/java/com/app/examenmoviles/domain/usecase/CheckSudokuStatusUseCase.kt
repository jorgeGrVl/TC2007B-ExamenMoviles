package com.app.examenmoviles.domain.usecase

import com.app.examenmoviles.domain.common.Result
import com.app.examenmoviles.domain.model.SudokuStatus
import com.app.examenmoviles.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSudokuStatusUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        operator fun invoke(
            puzzle: List<List<Int>>,
            width: Int,
            height: Int,
        ): Flow<Result<SudokuStatus>> =
            flow {
                try {
                    emit(Result.Loading)

                    val status = repository.checkStatus(puzzle, width, height)

                    emit(Result.Success(status))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
