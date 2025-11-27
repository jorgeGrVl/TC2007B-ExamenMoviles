package com.app.examenmoviles.presentation.screens.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: String? = null,
)
