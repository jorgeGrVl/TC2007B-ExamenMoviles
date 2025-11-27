package com.app.examenmoviles.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examenmoviles.presentation.screens.home.components.DropdownMenuDifficultySelector
import com.app.examenmoviles.presentation.screens.home.components.DropdownMenuSizeSelector

@Composable
fun HomeScreen(
    onNavigateToSudoku: (Int, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(0.9f)
                    .verticalScroll(rememberScrollState()) // 🔥 SCROLL
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 🔥 Título más grande
            Text(
                "Sudoku",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(Modifier.height(32.dp))

            // -----------------------------------------------------------
            // 🔥 Tarjeta de juego guardado
            // -----------------------------------------------------------
            if (state.hasSavedGame) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Column(
                        modifier =
                            Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "Partida Guardada",
                            style = MaterialTheme.typography.titleLarge,
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = {
                                viewModel.continueSavedGame {
                                    onNavigateToSudoku(0, "local")
                                }
                            },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text("Continuar partida")
                        }

                        Spacer(Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = { viewModel.deleteSavedGame() },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                            colors =
                                ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error,
                                ),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text("Eliminar partida")
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))
            }

            // -----------------------------------------------------------
            // 🔥 Tarjeta de configuración
            // -----------------------------------------------------------
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(3.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier =
                        Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "Nueva Partida",
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Spacer(Modifier.height(20.dp))

                    // Tamaño
                    Text(
                        "Tamaño del tablero",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.height(12.dp))

                    DropdownMenuSizeSelector(
                        selectedLabel = "${state.selectedSize} x ${state.selectedSize}",
                        onSelected = viewModel::updateSize,
                    )

                    Spacer(Modifier.height(28.dp))

                    // Dificultad
                    Text(
                        "Dificultad",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.height(12.dp))

                    DropdownMenuDifficultySelector(
                        selected = state.selectedDifficulty,
                        onSelected = viewModel::updateDifficulty,
                    )
                }

                Button(
                    onClick = {
                        viewModel.onGenerateSudoku { size, difficulty ->
                            onNavigateToSudoku(size, difficulty)
                        }
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text(
                        "Generar nuevo Sudoku",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}
