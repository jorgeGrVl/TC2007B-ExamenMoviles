package com.app.examenmoviles.domain.model

data class SudokuStatus(
    val status: String,
    val solution: List<List<Int>>?,
)
