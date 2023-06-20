package com.example.prayerstimes.domain.usecase

import com.example.prayerstimes.domain.model.TimingsEntity
import com.example.prayerstimes.domain.repo.PrayerRepository
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    suspend operator fun invoke(
        year: Int, month: Int, latitude: Double, longitude: Double, method: Int
    ): List<TimingsEntity>? {
        return prayerRepository.getPrayerTimes(year, month, latitude, longitude, method)
    }
}