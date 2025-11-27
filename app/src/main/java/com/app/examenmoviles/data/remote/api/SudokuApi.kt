package com.app.examenmoviles.data.remote.api

import com.app.examenmoviles.data.remote.dto.SudokuDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SudokuApi {
    @GET("sudokugenerate")
    suspend fun getSudoku(
        @Header("X-Api-Key") apiKey: String,
        @Query("width") width: Int,
        @Query("height") height: Int,
        @Query("difficulty") difficulty: String,
    ): SudokuDto
}
