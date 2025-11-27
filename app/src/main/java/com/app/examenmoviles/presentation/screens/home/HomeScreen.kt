package com.app.examenmoviles.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examenmoviles.presentation.screens.home.components.NewGameCard
import com.app.examenmoviles.presentation.screens.home.components.SavedGameCard

@Suppress("ktlint:standard:function-naming")
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Sudoku",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(Modifier.height(32.dp))

            if (state.hasSavedGame) {
                SavedGameCard(
                    onContinue = {
                        viewModel.continueSavedGame {
                            onNavigateToSudoku(0, "local")
                        }
                    },
                    onDelete = { viewModel.deleteSavedGame() },
                )
                Spacer(Modifier.height(32.dp))
            }

            NewGameCard(
                selectedSize = state.selectedSize,
                selectedDifficulty = state.selectedDifficulty,
                onSizeSelected = viewModel::updateSize,
                onDifficultySelected = viewModel::updateDifficulty,
                onGenerate = { size, difficulty ->
                    viewModel.onGenerateSudoku { s, d ->
                        onNavigateToSudoku(s, d)
                    }
                },
            )
        }
    }
}
