package com.app.examenmoviles.presentation.screens.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuScreen(
    size: Int,
    difficulty: String,
    viewModel: SudokuViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(size, difficulty)
    }

    val state by viewModel.uiState.collectAsState()

    if (state.board.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Cargando tablero…")
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Sudoku ($size x $size) - $difficulty")

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            repeat(state.size) { row ->
                Row {
                    repeat(state.size) { col ->
                        val value = state.board[row][col]
                        val isFixed = state.initialBoard[row][col] != null

                        Box(
                            modifier =
                                Modifier
                                    .size(40.dp)
                                    .border(1.dp, Color.Black)
                                    .background(
                                        if (isFixed) Color(0xFFDDE6FF) else Color.Transparent,
                                    ).let { baseModifier ->
                                        if (!isFixed) {
                                            baseModifier.clickable {
                                                val newValue =
                                                    if (value == null) {
                                                        1
                                                    } else {
                                                        ((value + 1) % state.size).takeIf { it != 0 }
                                                    }

                                                viewModel.updateCell(row, col, newValue)
                                            }
                                        } else {
                                            baseModifier // No clickable si es celda fija
                                        }
                                    },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = value?.toString() ?: "")
                        }
                    }
                }
            }
        }
    }
}
