package com.example.prayerstimes.domain.repo

import com.example.prayerstimes.domain.model.TimingsEntity


interface PrayerRepository {
    suspend fun getPrayerTimes(
        year: Int, month: Int, latitude: Double, longitude: Double, method: Int
    ): List<TimingsEntity>?

    suspend fun getPrayerDate(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int
    ): List<String>

    suspend fun getPrayerTimesOffline(): List<TimingsEntity>
    suspend fun getQiblaDirection(latitude: Double, longitude: Double): Double
}