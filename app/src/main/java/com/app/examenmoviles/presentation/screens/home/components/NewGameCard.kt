package com.app.examenmoviles.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun NewGameCard(
    selectedSize: Int,
    selectedDifficulty: String,
    onSizeSelected: (Int) -> Unit,
    onDifficultySelected: (String) -> Unit,
    onGenerate: (Int, String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(3.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Nueva Partida", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(20.dp))

            Text("Tamaño del tablero", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            DropdownMenuSizeSelector(
                selectedLabel = "$selectedSize x $selectedSize",
                onSelected = onSizeSelected,
            )

            Spacer(Modifier.height(28.dp))

            Text("Dificultad", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            DropdownMenuDifficultySelector(
                selected = selectedDifficulty,
                onSelected = onDifficultySelected,
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onGenerate(selectedSize, selectedDifficulty) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text("Generar nuevo Sudoku", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
