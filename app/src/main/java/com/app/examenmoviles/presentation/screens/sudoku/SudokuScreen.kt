package com.app.examenmoviles.presentation.screens.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
    onBack: () -> Unit = {}, // 🔥 botón para regresar
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(size, difficulty)
    }

    val state by viewModel.uiState.collectAsState()

    // -----------------------------
    //      LOADING
    // -----------------------------
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) { CircularProgressIndicator() }
        return
    }

    // -----------------------------
    //      ERROR
    // -----------------------------
    if (state.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Error: ${state.error}", color = Color.Red)
        }
        return
    }

    // -----------------------------
    //      UI PRINCIPAL
    // -----------------------------
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6FC))
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 🔙 Botón de regreso
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onBack) {
                Text("← Regresar", fontSize = 18.sp)
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Sudoku $size×$size",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = difficulty.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
        )

        Spacer(Modifier.height(24.dp))

        // -----------------------------
        //  TABLERO SCROLL + CARD
        // -----------------------------
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            val boardSizePx = maxWidth
            val cellSize = boardSizePx / state.size

            Card(
                modifier = Modifier.size(boardSizePx),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
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
                                            .background(
                                                if (isFixed) Color(0xFFE5ECFF) else Color.White,
                                            ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (isFixed) {
                                        Text(
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
                                                    LocalTextStyle.current.copy(
                                                        color = Color.Black,
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 20.sp,
                                                    ),
                                                modifier = Modifier.fillMaxSize(),
                                                singleLine = true,
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
        }

        Spacer(Modifier.height(20.dp))

        // -----------------------------
        //      BOTONES DE ACCIÓN
        // -----------------------------
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.checkSudoku() },
            ) { Text("Verificar") }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.resetSudoku() },
            ) { Text("Reiniciar") }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.onNewPuzzleRequested() },
            ) { Text("Nuevo") }
        }

        Spacer(Modifier.height(16.dp))

        // -----------------------------
        //   RESULTADO: CORRECTO / ERROR
        // -----------------------------
        state.isSolved?.let { solved ->
            Text(
                text = if (solved) "✔ ¡Correcto!" else "❌ Incorrecto",
                color = if (solved) Color(0xFF2E7D32) else Color.Red,
                fontSize = 20.sp,
            )
        }

        // -----------------------------
        //  DIALOGO DE GUARDAR PARTIDA
        // -----------------------------
        if (viewModel.showSaveGameDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.onDismissDialog() },
                title = { Text("Guardar partida") },
                text = { Text("¿Deseas guardar la partida actual antes de generar un nuevo tablero?") },
                confirmButton = {
                    TextButton(onClick = { viewModel.onSaveGameConfirmed() }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.onDiscardGameConfirmed() }) {
                        Text("No guardar")
                    }
                },
            )
        }
    }
}
