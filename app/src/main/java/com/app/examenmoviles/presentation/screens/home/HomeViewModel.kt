package com.app.examenmoviles.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmoviles.domain.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            fakeFetch().let { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(isLoading = true)
                        is Result.Success ->
                            state.copy(
                                data = result.data,
                                isLoading = false,
                                error = null,
                            )
                        is Result.Error ->
                            state.copy(
                                error = result.exception.message,
                                isLoading = false,
                            )
                    }
                }
            }
        }
    }

    private fun fakeFetch(): Result<String> =
        try {
            Result.Success("Hola desde el ViewModel")
        } catch (e: Exception) {
            Result.Error(e)
        }
}
