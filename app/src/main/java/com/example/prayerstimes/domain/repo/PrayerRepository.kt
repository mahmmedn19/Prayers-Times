package com.example.prayerstimes.domain.repo

import com.example.prayerstimes.domain.model.PrayerTimesEntity
import retrofit2.Response

interface PrayerRepository {
    suspend fun getPrayerTimes(
        year: String,
        month: String,
        latitude: String,
        longitude: String
    ): Response<List<PrayerTimesEntity>>
}