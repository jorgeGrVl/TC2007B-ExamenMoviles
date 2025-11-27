package com.app.examenmoviles.presentation.screens.sudoku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmoviles.data.local.model.SavedSudokuGame
import com.app.examenmoviles.data.local.preferences.SudokuPreferences
import com.app.examenmoviles.domain.common.Result
import com.app.examenmoviles.domain.usecase.CheckSudokuStatusUseCase
import com.app.examenmoviles.domain.usecase.GetSudokuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel
    @Inject
    constructor(
        private val getSudokuUseCase: GetSudokuUseCase,
        private val checkSudokuStatusUseCase: CheckSudokuStatusUseCase,
        private val sudokuPreferences: SudokuPreferences,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SudokuUiState())
        val uiState: StateFlow<SudokuUiState> = _uiState

        fun initialize(
            size: Int,
            difficulty: String,
        ) {
            if (difficulty == "local") {
                loadLocalGame()
                return
            }

            viewModelScope.launch {
                getSudokuUseCase(size, difficulty).collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                        is Result.Success -> {
                            val sudoku = result.data
                            _uiState.value =
                                SudokuUiState(
                                    size = size,
                                    difficulty = difficulty,
                                    board = sudoku.puzzle,
                                    initialBoard = sudoku.puzzle,
                                    isLoading = false,
                                )
                        }
                        is Result.Error ->
                            _uiState.value =
                                _uiState.value.copy(isLoading = false, error = result.exception.message)
                    }
                }
            }
        }

        private fun loadLocalGame() {
            val saved = sudokuPreferences.loadSavedGame() ?: return

            _uiState.value =
                SudokuUiState(
                    size = saved.size,
                    difficulty = "local",
                    board = saved.board,
                    initialBoard = saved.initialBoard,
                    isLoading = false,
                )
        }

        fun updateCell(
            row: Int,
            col: Int,
            value: Int?,
        ) {
            val current =
                _uiState.value.board
                    .map { it.toMutableList() }
                    .toMutableList()
            current[row][col] = value
            _uiState.value = _uiState.value.copy(board = current)
        }

        fun checkSudoku() {
            val state = _uiState.value
            val puzzleInt: List<List<Int>> =
                state.board.map { row ->
                    row.map { it ?: 0 }
                }

            viewModelScope.launch {
                checkSudokuStatusUseCase(puzzleInt, width = state.size, height = state.size).collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.value = state.copy(isLoading = true)
                        is Result.Success ->
                            _uiState.value =
                                state.copy(isLoading = false, isSolved = (state.board) == result.data.solution)
                        is Result.Error -> _uiState.value = state.copy(isLoading = false, error = result.exception.message)
                    }
                }
            }
        }

        fun resetSudoku() {
            val state = _uiState.value

            val resetBoard =
                List(state.size) { row ->
                    List(state.size) { col ->
                        // Si era fijo → mantener valor original
                        state.initialBoard[row][col]
                    }
                }

            _uiState.value =
                state.copy(
                    board = resetBoard,
                    isSolved = null,
                    resetCounter = state.resetCounter + 1,
                )
        }

        fun newSudoku() {
            initialize(
                size = _uiState.value.size,
                difficulty = _uiState.value.difficulty,
            )
        }

        var showSaveGameDialog by mutableStateOf(false)
            private set

        fun onNewPuzzleRequested() {
            showSaveGameDialog = true
        }

        fun onSaveGameConfirmed() {
            saveCurrentGame()
            showSaveGameDialog = false
            newSudoku()
        }

        fun onDiscardGameConfirmed() {
            showSaveGameDialog = false
            newSudoku()
        }

        fun onDismissDialog() {
            showSaveGameDialog = false
        }

        fun saveCurrentGame() {
            val state = _uiState.value

            val game =
                SavedSudokuGame(
                    board = state.board,
                    initialBoard = state.initialBoard,
                    size = state.size,
                    difficulty = state.difficulty,
                )

            sudokuPreferences.saveGame(game)
        }
    }
