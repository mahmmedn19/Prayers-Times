package com.example.prayerstimes.domain.usecase

import com.example.prayerstimes.domain.model.PrayerTimesEntity
import com.example.prayerstimes.domain.repo.PrayerRepository
import retrofit2.Response

class GetPrayerTimesUseCase(private val prayerRepository: PrayerRepository) {

    suspend operator fun invoke(year: String, month: String, latitude: String, longitude: String): Response<List<PrayerTimesEntity>> {
        return prayerRepository.getPrayerTimes(year, month, latitude, longitude)
    }
}