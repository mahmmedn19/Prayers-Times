/*
 * *
 *  * Created by Mohamed Naser on 6 , 2023.
 *  *
 *  * Copyright (c) 2023 All rights reserved.
 *  *
 *  * Last modified: 6/17/23, 9:34 PM
 *
 *
 */

package com.example.prayerstimes.ui.qiblah

import androidx.lifecycle.ViewModel
import com.example.prayerstimes.domain.usecase.GetQiblaUseCase
import com.example.prayerstimes.ui.uiState.QiblaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QiblahViewModel @Inject constructor(
    private val getQibla: GetQiblaUseCase
) : ViewModel() {

    private val _qiblaUiState = MutableStateFlow(QiblaUiState(isLoading = false))
    val qiblaUiState: StateFlow<QiblaUiState> = _qiblaUiState

    suspend fun fetchQiblaDirection(latitude: Double, longitude: Double) {
        _qiblaUiState.value = QiblaUiState(isLoading = true)
        try {
            val qiblaDirection = getQibla(latitude, longitude)
            _qiblaUiState.value = QiblaUiState(qiblaDirection = qiblaDirection, isLoading = false)
        } catch (e: Exception) {
            _qiblaUiState.value = QiblaUiState(
                isError = true,
                message = e.message ?: "Error occurred",
                isLoading = false
            )
        }
    }
}