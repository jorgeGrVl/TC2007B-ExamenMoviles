package com.app.examenmoviles.presentation.screens.sudoku.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Suppress("ktlint:standard:function-naming")
@Composable
fun SaveGameDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onDiscard: () -> Unit,
) {
    if (!show) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Guardar partida") },
        text = { Text("¿Deseas guardar la partida antes de crear una nueva?") },
        confirmButton = { TextButton(onClick = onSave) { Text("Guardar") } },
        dismissButton = { TextButton(onClick = onDiscard) { Text("No guardar") } },
    )
}
