package com.example.prayerstimes.ui.uiState

import com.example.prayerstimes.domain.model.TimingsEntity


data class PrayerTimesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val prayerDates: List<String>? = null,
    val prayerTimes: List<PrayerUiState>? = null
)

data class PrayerUiState(
    val fajr: String = "",
    val sunrise: String = "",
    val dhuhr: String = "",
    val asr: String = "",
    val maghrib: String = "",
    val isha: String = ""
)

fun List<TimingsEntity>.toPrayerUiStateList(): List<PrayerUiState> {
    return map { timingsEntity ->
        PrayerUiState(
            fajr = timingsEntity.fajr,
            sunrise = timingsEntity.sunrise,
            dhuhr = timingsEntity.dhuhr,
            asr = timingsEntity.asr,
            maghrib = timingsEntity.maghrib,
            isha = timingsEntity.isha
        )
    }
}
