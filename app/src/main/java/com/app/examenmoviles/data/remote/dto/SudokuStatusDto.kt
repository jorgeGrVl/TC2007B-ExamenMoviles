package com.app.examenmoviles.data.remote.dto

data class SudokuStatusDto(
    val status: String,
    val solution: List<List<Int>>?,
)
