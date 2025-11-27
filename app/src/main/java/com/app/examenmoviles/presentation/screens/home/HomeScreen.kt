package com.app.examenmoviles.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onNavigateToSudoku: (Int, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Generar Sudoku", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 Mostrar botones si hay juego guardado
        if (state.hasSavedGame) {
            Button(
                onClick = {
                    viewModel.continueSavedGame {
                        onNavigateToSudoku(0, "local")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Continuar partida") }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { viewModel.deleteSavedGame() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            ) {
                Text("Eliminar partida")
            }

            Spacer(Modifier.height(32.dp))
        }

        // Selectores normales
        Text("Tamaño del tablero")
        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuSizeSelector(
            selected = state.selectedSize,
            onSelected = viewModel::updateSize,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Dificultad")
        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuDifficultySelector(
            selected = state.selectedDifficulty,
            onSelected = viewModel::updateDifficulty,
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = {
            viewModel.onGenerateSudoku { size, difficulty ->
                onNavigateToSudoku(size, difficulty)
            }
        }) {
            Text("Generar nuevo Sudoku")
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun DropdownMenuSizeSelector(
    selected: Int,
    onSelected: (Int) -> Unit,
) {
    val sizes = listOf(4, 6, 9)
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Tamaño: $selected")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sizes.forEach {
                DropdownMenuItem(
                    text = { Text("$it") },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun DropdownMenuDifficultySelector(
    selected: String,
    onSelected: (String) -> Unit,
) {
    val difficulties = listOf("easy", "medium", "hard")
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Dificultad: $selected")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            difficulties.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    },
                )
            }
        }
    }
}
