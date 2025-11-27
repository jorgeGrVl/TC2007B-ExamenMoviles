package com.app.examenmoviles.presentation.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Suppress("ktlint:standard:function-naming")
@Composable
fun DropdownMenuDifficultySelector(
    selected: String,
    onSelected: (String) -> Unit,
) {
    val difficulties = listOf("easy", "medium", "hard")
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = true },
        ) {
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
