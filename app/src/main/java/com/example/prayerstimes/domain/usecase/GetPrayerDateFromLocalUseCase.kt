package com.example.prayerstimes.domain.usecase

import com.example.prayerstimes.domain.model.TimingsEntity
import com.example.prayerstimes.domain.repo.PrayerRepository
import javax.inject.Inject

class GetPrayerDateFromLocalUseCase @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    suspend operator fun invoke(
    ): List<TimingsEntity> {
        return prayerRepository.getPrayerTimesOffline()
    }
}