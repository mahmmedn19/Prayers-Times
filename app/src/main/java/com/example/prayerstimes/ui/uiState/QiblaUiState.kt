package com.example.prayerstimes.ui.uiState

data class QiblaUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val qiblaDirection: Double? = null
)
