package com.app.examenmoviles.presentation.screens.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examenmoviles.presentation.common.components.ErrorView
import com.app.examenmoviles.presentation.screens.sudoku.components.SaveGameDialog
import com.app.examenmoviles.presentation.screens.sudoku.components.SudokuBoard

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuScreen(
    size: Int,
    difficulty: String,
    viewModel: SudokuViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(size, difficulty)
    }

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6FC))
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onBack) {
                Text("← Regresar", fontSize = 18.sp)
            }
        }

        Spacer(Modifier.height(8.dp))

        when {
            state.isLoading -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 80.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                ErrorView(
                    message = "Sin conexión a internet. Intente de nuevo.",
                    onRetry = { viewModel.initialize(size, difficulty) },
                )
            }

            else -> {
                Text(
                    text = "Sudoku ${state.size}×${state.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    text = state.difficulty.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                )

                Spacer(Modifier.height(24.dp))

                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    SudokuBoard(state, viewModel, maxWidth)
                }

                Spacer(Modifier.height(20.dp))

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

                state.isSolved?.let { solved ->
                    Text(
                        text = if (solved) "✔ ¡Correcto!" else "❌ Incorrecto",
                        color = if (solved) Color(0xFF2E7D32) else Color.Red,
                        fontSize = 20.sp,
                    )
                }
            }
        }

        SaveGameDialog(
            show = viewModel.showSaveGameDialog,
            onDismiss = { viewModel.onDismissDialog() },
            onSave = { viewModel.onSaveGameConfirmed() },
            onDiscard = { viewModel.onDiscardGameConfirmed() },
        )
    }
}
