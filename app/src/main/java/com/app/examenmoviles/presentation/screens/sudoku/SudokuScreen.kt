package com.app.examenmoviles.presentation.screens.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // -----------------------------
    //      ESTADO: LOADING
    // -----------------------------
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Cargando tablero…")
        }
        return
    }

    // -----------------------------
    //      ESTADO: ERROR
    // -----------------------------
    if (state.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Error: ${state.error}",
                color = Color.Red,
            )
        }
        return
    }

    // -----------------------------
    //    SI NO HAY ERROR NI LOADING
    // -----------------------------
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
                                    .background(if (isFixed) Color(0xFFDDE6FF) else Color.White),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (isFixed) {
                                Text(
                                    text = value?.toString() ?: "",
                                    color = Color.Black,
                                )
                            } else {
                                var textValue by remember { mutableStateOf(value?.toString() ?: "") }

                                key(state.resetCounter, row, col) {
                                    // 👈 fuerza recreación del TextField
                                    var textValue by remember { mutableStateOf(value?.toString() ?: "") }

                                    BasicTextField(
                                        value = textValue,
                                        onValueChange = { newText ->
                                            if (newText.isEmpty()) {
                                                textValue = ""
                                                viewModel.updateCell(row, col, null)
                                                return@BasicTextField
                                            }

                                            val num = newText.toIntOrNull() ?: return@BasicTextField

                                            if (num in 1..state.size) {
                                                textValue = num.toString()
                                                viewModel.updateCell(row, col, num)
                                            }
                                        },
                                        textStyle =
                                            LocalTextStyle.current.copy(
                                                color = Color.Black,
                                                textAlign = TextAlign.Center,
                                                fontSize = 20.sp,
                                            ),
                                        modifier = Modifier.fillMaxSize(),
                                        singleLine = true,
                                        decorationBox = { innerTextField ->
                                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                                innerTextField()
                                            }
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Button(onClick = { viewModel.checkSudoku() }) {
                Text("Verificar solución")
            }

            Button(onClick = { viewModel.resetSudoku() }) {
                Text("Reiniciar")
            }

            Button(onClick = { viewModel.newSudoku() }) {
                Text("Nuevo")
            }
        }

        state.isSolved?.let { solved ->
            Text(
                text = if (solved) "¡Correcto! 🎉" else "Solución incorrecta ❌",
                color = if (solved) Color.Green else Color.Red,
            )
        }
    }
}
