package com.app.examenmoviles.data.local.model

data class SavedSudokuGame(
    val board: List<List<Int?>>,
    val initialBoard: List<List<Int?>>,
    val size: Int,
    val difficulty: String,
)
