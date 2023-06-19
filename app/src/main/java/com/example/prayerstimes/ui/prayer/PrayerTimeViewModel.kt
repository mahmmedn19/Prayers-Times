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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayerstimes.domain.usecase.GetPrayerTimesUseCase
import com.example.prayerstimes.ui.uiState.PrayerTimesUiState
import com.example.prayerstimes.ui.uiState.toPrayerUiStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(private val getPrayerTimesUseCase: GetPrayerTimesUseCase) :
    ViewModel(),
    PrayerTimeListener {
    /*
        private val _prayerTimesUiState = MutableStateFlow(null)
        val prayerTimesUiState = _prayerTimesUiState
    */

    private val _prayerTimesUiState = MutableLiveData<PrayerTimesUiState>()
    val prayerTimesUiState: LiveData<PrayerTimesUiState> get() = _prayerTimesUiState

    init {
        fetchPrayerTimes()
    }

    private fun fetchPrayerTimes() {
        viewModelScope.launch(Dispatchers.IO) {
            _prayerTimesUiState.postValue(PrayerTimesUiState(isLoading = true))
            try {
                val prayerTimes = getPrayerTimesUseCase(
                    2023,
                    6,
                    30.044565856710083,
                    31.233066978107168, 5
                )
                val prayerUiState = prayerTimes?.toPrayerUiStateList()
                _prayerTimesUiState.postValue(
                    PrayerTimesUiState(prayerTimes = prayerUiState, isLoading = false)
                )
            } catch (e: Exception) {
                _prayerTimesUiState.postValue(
                    PrayerTimesUiState(
                        isError = true,
                        message = e.message ?: "Error occurred",
                        isLoading = false
                    )
                )
            }
        }
    }


    override fun onClickPrayer(prayerTime: Long) {
        TODO("Not yet implemented")
    }
}