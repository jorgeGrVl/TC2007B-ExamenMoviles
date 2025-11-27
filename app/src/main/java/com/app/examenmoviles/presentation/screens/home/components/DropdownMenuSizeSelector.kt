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
fun DropdownMenuSizeSelector(
    selectedLabel: String,
    onSelected: (Int) -> Unit,
) {
    val sizes = listOf(4, 9)
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = true },
        ) {
            Text("Tamaño: $selectedLabel")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sizes.forEach {
                DropdownMenuItem(
                    text = { Text("$it x $it") },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    },
                )
            }
        }
    }
}
