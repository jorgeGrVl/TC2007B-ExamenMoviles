package com.app.examenmoviles.presentation.screens.home

data class HomeUiState(
    val selectedSize: Int = 9,
    val selectedDifficulty: String = "easy",
    val isLoading: Boolean = false,
    val error: String? = null,
)
