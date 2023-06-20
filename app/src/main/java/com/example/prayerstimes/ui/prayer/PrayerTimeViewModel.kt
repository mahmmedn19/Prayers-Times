/*
 * *
 *  * Created by Mohamed Naser on 6 , 2023.
 *  *
 *  * Copyright (c) 2023 All rights reserved.
 *  *
 *  * Last modified: 6/17/23, 9:33 PM
 *
 *
 */

package com.example.prayerstimes.ui.prayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerstimes.domain.usecase.GetPrayerDateFromLocalUseCase
import com.example.prayerstimes.domain.usecase.GetPrayerDateUseCase
import com.example.prayerstimes.domain.usecase.GetPrayerTimesUseCase
import com.example.prayerstimes.ui.uiState.PrayerTimesUiState
import com.example.prayerstimes.ui.uiState.toPrayerUiStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getPrayerDateUseCase: GetPrayerDateUseCase,
    private val getPrayerDateFromLocalUseCase: GetPrayerDateFromLocalUseCase
    ) :
    ViewModel(),
    PrayerTimeListener {
    private val _prayerTimesUiState = MutableStateFlow(PrayerTimesUiState(isLoading = true))
    val prayerTimesUiState: StateFlow<PrayerTimesUiState> = _prayerTimesUiState


    private val _prayerDates = MutableStateFlow<List<String>>(emptyList())
    val prayerDates: StateFlow<List<String>> = _prayerDates

    fun fetchPrayerTimes(year: Int, month: Int, latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _prayerTimesUiState.value = PrayerTimesUiState(isLoading = true)
            try {
                val prayerTimes = getPrayerTimesUseCase(year, month, latitude, longitude, 5)
                val prayerDates = getPrayerDateUseCase(year, month, latitude, longitude, 5)
                _prayerDates.value = prayerDates
                val prayerUiState = prayerTimes?.toPrayerUiStateList()
                _prayerTimesUiState.value =
                    PrayerTimesUiState(prayerTimes = prayerUiState,prayerDates = prayerDates, isLoading = false)
            } catch (e: Exception) {
                _prayerTimesUiState.value = PrayerTimesUiState(
                    isError = true,
                    message = e.message ?: "Error occurred",
                    isLoading = false
                )
            }
        }
    }
    fun fetchPrayerTimesOffline() {
        viewModelScope.launch {
            val times = getPrayerDateFromLocalUseCase()
            val prayerUiStateList = times.toPrayerUiStateList()
            _prayerTimesUiState.value = PrayerTimesUiState(
                isLoading = false,
                isError = false,
                message = "",
                prayerDates = null,
                prayerTimes = prayerUiStateList
            )
        }
    }


    override fun onClickPrayer(prayerTime: Long) {

    }

}