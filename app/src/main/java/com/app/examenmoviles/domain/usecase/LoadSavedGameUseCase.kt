package com.app.examenmoviles.domain.usecase

import com.app.examenmoviles.data.local.model.SavedSudokuGame
import com.app.examenmoviles.domain.repository.SudokuRepository
import javax.inject.Inject

class LoadSavedGameUseCase
    @Inject
    constructor(
        private val repository: SudokuRepository,
    ) {
        suspend operator fun invoke(): SavedSudokuGame? = repository.loadSavedGame()
    }
