package com.app.examenmoviles.domain.usecase

import com.app.examenmoviles.domain.repository.SudokuRepository
import javax.inject.Inject

class DeleteSavedGameUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        suspend operator fun invoke() = repository.deleteSavedGame()
    }
