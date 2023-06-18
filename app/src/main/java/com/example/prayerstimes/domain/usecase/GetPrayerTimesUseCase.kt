package com.example.prayerstimes.domain.usecase

import com.example.prayerstimes.domain.model.PrayerTimesEntity
import com.example.prayerstimes.domain.repo.PrayerRepository
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor
    (private val prayerRepository: PrayerRepository) {

    suspend operator fun invoke(
        year: String,
        month: String,
        latitude: String,
        longitude: String,
        method: String
    ): PrayerTimesEntity? {
        return prayerRepository.getPrayerTimes(year, month, latitude, longitude, method)
    }
}