package com.app.examenmoviles.presentation.screens.sudoku.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.examenmoviles.presentation.screens.sudoku.SudokuUiState
import com.app.examenmoviles.presentation.screens.sudoku.SudokuViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuBoard(
    state: SudokuUiState,
    viewModel: SudokuViewModel,
    boardSize: Dp,
) {
    val cellSize = boardSize / state.size

    Column(
        modifier = Modifier.size(boardSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        repeat(state.size) { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(state.size) { col ->
                    val value = state.board[row][col]
                    val isFixed = state.initialBoard[row][col] != null

                    Box(
                        modifier =
                            Modifier
                                .size(cellSize)
                                .border(1.dp, Color(0xFF6A6A6A))
                                .background(if (isFixed) Color(0xFFE5ECFF) else Color.White),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isFixed) {
                            androidx.compose.material3.Text(
                                text = value?.toString() ?: "",
                                color = Color.Black,
                                fontSize = 20.sp,
                            )
                        } else {
                            key(state.resetCounter, row, col) {
                                var textValue by remember { mutableStateOf(value?.toString() ?: "") }

                                BasicTextField(
                                    value = textValue,
                                    onValueChange = { newText ->
                                        if (newText.isEmpty()) {
                                            textValue = ""
                                            viewModel.updateCell(row, col, null)
                                        } else {
                                            val num = newText.toIntOrNull()
                                            if (num != null && num in 1..state.size) {
                                                textValue = num.toString()
                                                viewModel.updateCell(row, col, num)
                                            }
                                        }
                                    },
                                    textStyle =
                                        androidx.compose.ui.text.TextStyle(
                                            color = Color.Black,
                                            textAlign = TextAlign.Center,
                                            fontSize = 20.sp,
                                        ),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxSize(),
                                    decorationBox = { inner ->
                                        Box(
                                            Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center,
                                        ) { inner() }
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
