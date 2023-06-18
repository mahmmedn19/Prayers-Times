package com.example.prayerstimes.data.remote.model

data class PrayerTimesResponse(
    val code: Int,
    val status: String,
    val data: List<PrayerTimesDto>
)
