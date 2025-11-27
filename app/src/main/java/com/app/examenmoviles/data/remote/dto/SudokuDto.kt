package com.app.examenmoviles.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SudokuDto(
    val puzzle: List<List<Int?>>,
    val solution: List<List<Int>>,
)
